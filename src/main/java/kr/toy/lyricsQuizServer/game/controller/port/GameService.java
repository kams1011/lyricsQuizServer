package kr.toy.lyricsQuizServer.game.controller.port;

public interface GameService {

    public void 로비_게임리스트_조회();
    public void 방_제목_검색();
    public void 방장_닉네임_검색();
    public void 방_생성();
    public void 같이_할_사람_검색(); // TODO 현재 접속중인 인원중에 현재 게임을 진행중이지 않은 인원 ( 현재 접속여부는 다시 생각해보기 )
}
