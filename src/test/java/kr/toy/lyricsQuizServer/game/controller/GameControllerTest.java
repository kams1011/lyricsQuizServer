package kr.toy.lyricsQuizServer.game.controller;

import kr.toy.lyricsQuizServer.docs.game.GameRestDocs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(GameController.class)
public class GameControllerTest extends GameRestDocs {


    @Test
    public void get_game_list_test(){
        initializeDummyDataList();

    }

    @Test
    public void get_quiz_summary_list_test(){


    }

    @Test
    public void create_test(){


    }

    @Test
    public void check_game_password_test(){


    }

    @Test
    public void get_user_is_host_test(){


    }

    @Test
    public void enter_test(){


    }

    @Test
    public void allow_invitation_test(){


    }

    @Test
    public void get_my_invitation_status_test(){


    }

    @Test
    public void game_start_test(){


    }

    @Test
    public void get_invitable_users_test(){


    }

    @Test
    public void invite_test(){


    }

    @Test
    public void ready_test(){


    }

    @Test
    public void streaming_complete_test(){


    }

    @Test
    public void check_answer_test(){


    }





}
