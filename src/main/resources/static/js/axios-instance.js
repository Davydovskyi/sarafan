import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    // Другие настройки, если необходимо
})

export default {
    add: message => instance.post('/messages', message),
    update: (id, message) => instance.put(`/messages/${id}`, message),
    delete: id => instance.delete(`/messages/${id}`)
}