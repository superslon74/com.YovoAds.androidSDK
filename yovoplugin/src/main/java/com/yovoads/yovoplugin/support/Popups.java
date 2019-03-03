package com.yovoads.yovoplugin.support;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;

public class Popups {

    public Popups() {
    }

    private static int GetTheme() {
        int _theme = Build.VERSION.SDK_INT >= 21 ? 16974393 : 16973935;
        return _theme;
    }

    public void Show(String _title, String _mess, String _butYes) {
        AlertDialog.Builder _builder = CreatePopups(_title, _mess, _butYes);
        _builder.show();
    }

    public void Show(String _title, String _mess, String _butYes, String _butNo) {
        AlertDialog.Builder _builder = CreatePopups(_title, _mess, _butYes);
        _builder.setNegativeButton(_butNo, dialogClickListener);
        _builder.show();
    }

    public void Show(String _title, String _mess, String _butYes, String _butNo, String _butLaiter) {
        AlertDialog.Builder _builder = CreatePopups(_title, _mess, _butYes);
        _builder.setNegativeButton(_butNo, dialogClickListener);
        _builder.setNeutralButton(_butLaiter, dialogClickListener);
        _builder.show();
    }

    private AlertDialog.Builder CreatePopups(String _title, String _mess, String _butYes) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(new ContextThemeWrapper(DI.m_activity, GetTheme()));
        _builder.setTitle(_title);
        _builder.setMessage(_mess);
        _builder.setPositiveButton(_butYes, dialogClickListener);
        _builder.setOnKeyListener(KeyListener);
        _builder.setCancelable(false);
        return _builder;
    }

    private DialogInterface.OnKeyListener KeyListener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == 4) {
                YovoSDK.mi_OnUnitySDK.OnPopupsShow(-1);
                dialog.dismiss();
            }
            return false;
        }
    };

    private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -1:
                   YovoSDK.mi_OnUnitySDK.OnPopupsShow(1);
                    break;
                case -2:
                    YovoSDK.mi_OnUnitySDK.OnPopupsShow(-99);
                    break;
                case -3:
                    YovoSDK.mi_OnUnitySDK.OnPopupsShow(0);
            }
            dialog = null;
        }
    };

    public void ShowAppTryQuit(int _language) {
        if (_language == 30) {
            AppTryQuit("Выход из игры.", "Вы действительно хотите выйти ?", "Выход", "Продолжить");
        } else if (_language == 28) {
            AppTryQuit("Saia do jogo.", "Você tem certeza que quer sair ?", "Saída", "Continuar");
        } else if (_language == 34) {
            AppTryQuit("Sal del juego.", "Seguro que quieres salir ?", "Salida", "Continuar");
        } else if (_language == 15) {
            AppTryQuit("Verlasse das Spiel.", "Sind Sie sicher, dass Sie aufhören wollen ?", "Ausgang", "Fortsetzen");
        } else if (_language == 14) {
            AppTryQuit("Quitte le jeu.", "Êtes-vous sûr de vouloir quitter ?", "Sortie", "Continuer");
        } else if (_language == 21) {
            AppTryQuit("Esci dal gioco.", "Sei sicuro di voler uscire ?", "Uscita", "Continua");
        } else if (_language == 27) {
            AppTryQuit("Wyjdź z gry.", "Czy na pewno chcesz zakończyć ?", "Wyjście", "dalej");
        } else if (_language == 1) {
            AppTryQuit("الخروج من اللعبة.", "هل أنت متأكد من أنك تريد الخروج ؟", "ىخرج", "استمر");
        } else if (_language == 7) {
            AppTryQuit("Ukončete hru.", "Jste si jisti, že chcete skončit ?", "Výstup", "Pokračovat");
        } else if (_language == 16) {
            AppTryQuit("Έξοδος από το παιχνίδι.", "Είσαι σίγουρος ότι θέλεις να παραιτηθείς ?", "Εξοδος", "Να συνεχίσει");
        } else if (_language == 20) {
            AppTryQuit("Keluar dari permainan.", "Anda yakin ingin berhenti ?", "Keluar", "Terus");
        } else if (_language == 22) {
            AppTryQuit("ゲームを終了する。", "本当にやめる気 ？", "出口", "持続する");
        } else if (_language == 23) {
            AppTryQuit("게임을 종료하십시오.", "종료 하시겠습니까 ?", "출구", "잇다");
        } else if (_language == 37) {
            AppTryQuit("Oyundan çıkın.", "Çıkmak istediğinden emin misin ?", "çıkış", "Devam et");
        } else if (_language == 38) {
            AppTryQuit("Вийти з гри.", "Ти впевнений що хочеш піти ?", "Вийти", "Продовжуй");
        } else {
            AppTryQuit("Exit the game.", "Are you sure you want to quit ?", "Exit", "Continue");
        }
    }

    private void AppTryQuit(String _title, String _mess, String _butYes, String _butNo) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(new ContextThemeWrapper(DI.m_activity, GetTheme()));
        _builder.setTitle(_title);
        _builder.setMessage(_mess);
        _builder.setPositiveButton(_butYes, AppTryQuitListener);
        _builder.setNegativeButton(_butNo, AppTryQuitListener);
        _builder.setOnKeyListener(AppTryQuitEscListener);
        _builder.setCancelable(false);
        _builder.show();
    }

    private DialogInterface.OnKeyListener AppTryQuitEscListener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == 4) {
                YovoSDK.mi_OnUnitySDK.OnAppTryQuit(-1);
                dialog.dismiss();
            }
            return false;
        }
    };

    private DialogInterface.OnClickListener AppTryQuitListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -1:
                    YovoSDK.mi_OnUnitySDK.OnAppTryQuit(1);
                    break;
                case -2:
                    YovoSDK.mi_OnUnitySDK.OnAppTryQuit(0);
            }
            dialog = null;
        }
    };
}
