package ui;

import ai.AI;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PVEBoardPanel extends PBoardPanel {
    private boolean aiCalculating = false;//ai正在计算
    private AI ai;//ai
    private boolean mUserColorBlack = true;//人是黑棋

    public void setUserColorBlack(boolean userColorBlack) {
        mUserColorBlack = userColorBlack;
        ai = new AI();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int c = e.getButton();
        System.out.println("x = " + e.getPoint().x + "   y = " + e.getPoint().y);
        if (finish || aiCalculating) {
            System.out.println("return");
            return;
        }
        //左键按下
        if (c == MouseEvent.BUTTON1) {
            System.out.println("xxxxx");
            Point raw = e.getPoint();
            Point p = getStandardPx(raw.x, raw.y);
            System.out.println("a");
            if (p.x < 25 || p.y < 25)
                return;
            System.out.println("b");
            if (havePiece(p.x, p.y) || finish) {
                return;
            }
            System.out.println("d");
            drawRealPiece(p.x, p.y);
            if (finish){
                return;
            }
            aiCalculating = true;
            System.out.println("ing");
            //ai落子

            Point last = px2index(p.x, p.y);
            Point res = ai.simple(panel, last);
            System.out.println(res.x + " , " + res.y);
            drawRealPieceIndex(res.x, res.y);//ai保证p点可以落子
            System.out.println("ing");
            aiCalculating = false;

        }
    }
}
