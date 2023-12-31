const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  lintOnSave:false,
  transpileDependencies: true,
  outputDir: "../src/main/resources/static",  // 빌드 타겟 디렉토리
  devServer: {
    proxy: {
      '/api': {
        // '/api' 로 들어오면 포트 80(스프링 서버)로 보낸다
        target: 'https://localhost:80',
        changeOrigin: true, // cross origin 허용
      }
    },
    https: true
  }
})
