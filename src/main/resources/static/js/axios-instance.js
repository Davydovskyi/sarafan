import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    // Другие настройки, если необходимо
});

export default instance;