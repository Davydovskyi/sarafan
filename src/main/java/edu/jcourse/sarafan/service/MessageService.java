package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.EventType;
import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.dto.MetaData;
import edu.jcourse.sarafan.dto.ObjectType;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.mapper.MessageMapper;
import edu.jcourse.sarafan.repository.MessageRepository;
import edu.jcourse.sarafan.util.WsSender;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class MessageService {
    private static final String URL_PATTERN = "https?://?[\\w.\\-%/?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final BiConsumer<EventType, MessageDto> wsSender;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.IdName.class);
    }


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
                .map(this::fillMeta)
                .map(messageMapper::toEntity)
                .map(messageRepository::save)
                .map(messageMapper::toDto)
                .map(dto -> {
                    wsSender.accept(EventType.CREATED, dto);
                    return dto;
                })
                .orElseThrow();
    }

    @Transactional
    public Optional<MessageDto> update(Long id, MessageDto messageDto) {
        return messageRepository.findById(id)
                .map(entity -> {
                    messageMapper.copy(fillMeta(messageDto), entity);
                    return entity;
                })
                .map(messageRepository::saveAndFlush)
                .map(messageMapper::toDto)
                .map(dto -> {
                    wsSender.accept(EventType.UPDATED, dto);
                    return dto;
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return messageRepository.findById(id)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    wsSender.accept(EventType.DELETED, messageMapper.toDto(entity));
                    return true;
                })
                .orElse(false);
    }

    private MessageDto fillMeta(MessageDto message) {
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