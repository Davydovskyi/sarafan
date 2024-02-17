import {createStore} from 'vuex'
import axiosInstance from 'axios-instance'

function findMessageIndex(state, messageId) {
    return state.messages.findIndex(m => m.id === messageId)
}

export const store = createStore({
    state() {
        return {
            messages,
            profile: frontendData.profile,
            metaData: frontendData.metaData
        }
    },
    getters: {
        sortedMessages(state) {
            return state.messages.sort((a, b) => b.id - a.id)
        }
    },
    mutations: {
        addMessageMutation(state, message) {
            const index = findMessageIndex(state, message.id)
            if (index === -1) {
                state.messages.push(message)
            } else {
                state.messages[index] = message
            }
        },
        updateMessageMutation(state, message) {
            const index = findMessageIndex(state, message.id)
            if (index !== -1) {
                state.messages[index] = message
            }
        },
        deleteMessageMutation(state, id) {
            const index = findMessageIndex(state, id)
            if (index !== -1) {
                state.messages.splice(index, 1)
            }
        },
        addCommentMutation(state, comment) {
            const message = state.messages[findMessageIndex(state, comment.message.id)]
            message.comments = message.comments || []
            if (!message.comments.find(c => c.id === comment.id)) {
                message.comments.push(comment)
            }
        },
        addMessagePageMutation(state, newMessages) {
            const updatedMessages = newMessages.reduce((result, message) => {
                result[message.id] = message
                return result
            }, {...state.messages})
            state.messages = Object.values(updatedMessages)
        },
        updateTotalPagesMutation(state, totalPages) {
            state.metaData.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage) {
            state.metaData.page = currentPage
        }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            try {
                const response = await axiosInstance.addMessage(message)
                const index = await findMessageIndex(state, message.id)
                if (index === -1) {
                    commit('addMessageMutation', response.data)
                } else {
                    commit('updateMessageMutation', response.data)
                }
            } catch (e) {
                console.error('Error submitting message:', e)
            }
        },
        async updateMessageAction({commit}, message) {
            try {
                const response = await axiosInstance.updateMessage(message.id, message)
                commit('updateMessageMutation', response.data)
            } catch (e) {
                console.error('Error updating message:', e)
            }
        },
        async deleteMessageAction({commit}, id) {
            try {
                const response = await axiosInstance.deleteMessage(id)
                if (response.status === 204) {
                    commit('deleteMessageMutation', id)
                }
            } catch (e) {
                console.error('Error deleting message:', e)
            }
        },
        async addCommentAction({commit}, comment) {
            try {
                const response = await axiosInstance.addComment(comment)
                commit('addCommentMutation', response.data)
            } catch (e) {
                console.error('Error adding comment:', e)
            }
        },
        async loadPageAction({commit, state}) {
            try {
                const response = await axiosInstance.loadPage(state.metaData.page + 1)
                const {content, meta_data} = response.data
                const {total_pages, page} = meta_data
                commit('addMessagePageMutation', content)
                commit('updateTotalPagesMutation', total_pages)
                commit('updateCurrentPageMutation', Math.min(page, total_pages - 1))
            } catch (e) {
                console.error('Error loading page:', e)
            }
        }
    }
})