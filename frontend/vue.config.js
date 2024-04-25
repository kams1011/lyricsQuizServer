const { defineConfig } = require('@vue/cli-service');
const path = require('path');


module.exports = defineConfig({
  lintOnSave: false,
  transpileDependencies: true,
  outputDir: "../src/main/resources/static", // 빌드 타겟 디렉토리 설정

  devServer: {
    proxy: {
      '/api': {
        // '/api'로 들어오면 포트 80(스프링 서버)로 보낸다
        target: 'https://lyricsquizkaams.site',
        changeOrigin: true, // cross origin 허용
      }
    },
    https: true
  },

  configureWebpack: {
    output: {
      // Template for index.html
      path: path.resolve(__dirname, '../src/main/resources/static'), // Vue 프로젝트의 빌드 결과물 위치 변경 설정
    },
  },

  chainWebpack: config => {
    // Modify the assets subdirectory and public path
    config.plugin('html').tap(args => {
      args[0].filename = path.resolve(__dirname, '../src/main/resources/static/index.html');
      return args;
    });
  },
});
