// src/store/index.js
import { createStore } from 'vuex';

export default createStore({
    state: {
        // 여기에 전역적으로 관리하고자 하는 변수를 추가
        dynamicVariable: 'initialValue',
    },
    mutations: {
        // 변수를 변경하는 뮤테이션 추가
        setDynamicVariable(state, newValue) {
            state.dynamicVariable = newValue;
        },
    },
    actions: {
        // 변수를 변경하는 액션 추가
        updateDynamicVariable({ commit }, newValue) {
            commit('setDynamicVariable', newValue);
        },
    },
    getters: {
        // 변수를 가져오는 게터 추가
        getDynamicVariable: (state) => state.dynamicVariable,
    },
});
