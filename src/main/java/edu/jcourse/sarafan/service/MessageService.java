package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.dto.MetaData;
import edu.jcourse.sarafan.entity.Message;
import edu.jcourse.sarafan.mapper.MessageMapper;
import edu.jcourse.sarafan.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private static final String URL_PATTERN = "https?://?[\\w.\\-%/?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";
    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public List<MessageDto> findAll() {
        return messageRepository
                .findAll()
                .stream()
                .map(messageMapper::toDto)
                .toList();
    }

    public Optional<MessageDto> findById(Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDto);
    }

    @Transactional
    public MessageDto create(MessageDto messageDto) {
        return Optional.of(messageDto)
                .map(messageMapper::toEntity)
                .map(messageRepository::save)
                .map(messageMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<MessageDto> update(Long id, MessageDto messageDto) {
//       1. MessageDto build = messageDto.toBuilder().id(id).build();
        // или так
        return messageRepository.findById(id)
                .map(entity -> {
//                    BeanUtils.copyProperties(messageDto, entity, MessageDto.Fields.id); // 2.
                    messageMapper.copy(messageDto, entity);  // 3
                    return entity;
                })
                .map(messageRepository::saveAndFlush)
                .map(messageMapper::toDto);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return messageRepository.findById(id)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public MessageDto fillMeta(MessageDto message) {
        String text = message.text();
        Matcher urlMatcher = URL_REGEX.matcher(text);
        if (urlMatcher.find()) {
            String url = text.substring(urlMatcher.start(), urlMatcher.end());
            MessageDto.MessageDtoBuilder messageBuilder = message.toBuilder().link(url);
            Matcher imageMatcher = IMAGE_REGEX.matcher(url);
            if (imageMatcher.find()) {
                messageBuilder.linkCover(url);
            } else if (!url.contains("youtu")) {
                MetaData metaData = getMetaData(url);
                messageBuilder
                        .linkTitle(metaData.title())
                        .linkDescription(metaData.description())
                        .linkCover(metaData.cover());
            }
            return messageBuilder.build();
        }
        return message;
    }

    @SneakyThrows
    private MetaData getMetaData(String url) {
        Connection.Response response = Jsoup.connect(url).execute();
        Document doc = response.parse();

        return MetaData.builder()
                .title(getContent(doc, "meta[name$=title], meta[property$=title]"))
                .description(getContent(doc, "meta[name$=description], meta[property$=description]"))
                .cover(getContent(doc, "meta[name$=image], meta[property$=image]"))
                .build();
    }

    private String getContent(Document doc, String cssQuery) {
        Element element = doc.select(cssQuery).first();
        return element != null ? element.attr("content") : "";
    }
}