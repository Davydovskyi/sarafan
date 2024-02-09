import {createStore} from 'vuex'
import axiosInstance from 'axios-instance'

function findMessageIndex(state, messageId) {
    return state.messages.findIndex(m => m.id === messageId)
}

export const store = createStore({
    state() {
        return {
            messages: frontendData.messages,
            profile: frontendData.profile
        }
    },
    getters: {
        sortedMessages(state) {
            return state.messages.sort((a, b) => b.id - a.id)
        }
    },
    mutations: {
        addMessageMutation(state, message) {
            state.messages.push(message)
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
        }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            try {
                const response = await axiosInstance.add(message)
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
                const response = await axiosInstance.update(message.id, message)
                commit('updateMessageMutation', response.data)
            } catch (e) {
                console.error('Error updating message:', e)
            }
        },
        async deleteMessageAction({commit}, id) {
            try {
                const response = await axiosInstance.delete(id)
                if (response.status === 204) {
                    commit('deleteMessageMutation', id)
                }
            } catch (e) {
                console.error('Error deleting message:', e)
            }
        }
    }
})