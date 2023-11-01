// vite.config.js
import { defineConfig } from 'vite';

export default defineConfig({
    build: {
        // 다른 빌드 옵션들...
    },
    define: {
        // .env 파일의 변수들을 Vite에 전달
        'process.env': process.env,
    },
});
