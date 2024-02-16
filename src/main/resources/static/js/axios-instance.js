import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    // Другие настройки, если необходимо
})

export default {
    addMessage: message => instance.post('/messages', message),
    updateMessage: (id, message) => instance.put(`/messages/${id}`, message),
    deleteMessage: id => instance.delete(`/messages/${id}`),

    addComment: comment => instance.post('/comments', comment)
}