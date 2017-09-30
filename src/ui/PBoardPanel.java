package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class PBoardPanel extends JPanel implements MouseListener {

    //成功回调函数
    public interface OnSuccessListener {
        void win(boolean isBlack);
    }
 
    protected OnSuccessListener listener;

    public void setOnSuccessListener(OnSuccessListener l) {
        listener = l;
    }

    protected static boolean DEBUG = true;
    protected final int lineCount = 15;//线数
    protected final int lineLength = 50;//棋盘间隔
    protected final int startX = 50;//棋盘起始x位置
    protected final int startY = 50;//棋盘起始y位置

    protected final int itemLenght = lineLength * 3 / 4;//棋子大小

    protected final int bottom = startY + (lineCount - 1) * lineLength;//底边位置
    protected final int right = startX + (lineCount - 1) * lineLength;//右边位置
    protected boolean isBlack = true;//当前是否应该黑棋

    protected Graphics2D g;//画图

    protected Color whiteColor = Color.GRAY;//白棋颜色
    protected Color blackColor = Color.BLACK;//黑棋颜色
    protected final int MAX_PIECE = 5;//五子棋
    protected boolean finish = true;

    protected BufferedImage blackImage;
    protected BufferedImage whiteImage;
    protected int[][] panel = new int[lineCount][lineCount];//棋盘 1黑 -1白 0空

    public PBoardPanel() {
        initPanel();
        loadImage();
    }

    protected void loadImage() {
        try {
            blackImage = ImageIO.read(getClass().getResource("/black.png"));
            whiteImage = ImageIO.read(getClass().getResource("/white.png"));
        } catch (IOException e) {
            System.out.println("null image");
            e.printStackTrace();
        }
    }

    protected void initPanel() {
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                panel[i][j] = 0;
            }
        }
    }

    public void setGraphics(Graphics graphics) {
        if (graphics == null) {
            throw new NullPointerException("g is null");
        }
        this.g = (Graphics2D) graphics;
    }

    public void drawBoard() {
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRect(startX - 25, startY - 25, right + 25, bottom + 25);
        drawRealBoard();
        drawRealText();
    }

    /**
     * 画棋盘
     */
    protected void drawRealBoard() {
        for (int i = 0; i < lineCount; i++) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1));
            //col
            int sx = startX + i * lineLength;
            int sy = startY;
            int ex = sx;
            int ey = startY + (lineCount - 1) * lineLength;
            //System.out.println("col:  sx = " + sx + "  sy = " + sy + "   ex = " + ex + "  ey = " + ey);
            g.drawLine(sx, sy, ex, ey);
            //row
            sx = startX;
            sy = startY + i * lineLength;
            ex = startX + (lineCount - 1) * lineLength;
            ey = sy;
            g.drawLine(sx, sy, ex, ey);
            //6 System.out.println("row:  sx = " + sx + "  sy = " + sy + "   ex = " + ex + "  ey = " + ey);
        }
    }

    protected void drawBlackPiece(int x, int y) {
        g.drawImage(blackImage, x, y, itemLenght, itemLenght, null);
    }

    protected void drawWhitePiece(int x, int y) {
        g.drawImage(whiteImage, x, y, itemLenght, itemLenght, null);
    }

    public void clear() {
        isBlack = true;


        drawBoard();
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                panel[i][j] = 0;
            }
        }
    }

    /**
     * 画棋盘旁边的字
     */
    protected void drawRealText() {
        int mid = lineLength / 2;
        int bx = startX;
        int by = bottom + mid;
        int rx = right + mid;
        int ry = startY;
        char bottomChar = 'a';
        int rightInt = 1;
        for (int i = 0; i < lineCount; i++) {
            g.drawString(bottomChar + "", bx, by);
            g.drawString(rightInt + "", rx, ry);
            bx += lineLength;
            ry += lineLength;
            bottomChar++;
            rightInt++;
        }
    }

    public void drawPiece(Point p) {
        drawPiece(p.x, p.y);
    }

    public void drawPiece(int x, int y) {
        if (!(x < right + 20 && x > startX && y < bottom + 20 && y > startY)) {
            return;
        }
        Point p = getStandardPx(x, y);
        System.out.println("draw real piece");
        drawRealPiece(p.x, p.y);
    }

    /**
     * 重绘所有棋子
     */
    public void drawAllPiece() {
        System.out.println("draw all piece");
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                Point point = index2px(i, j);
                if (panel[i][j] == 1) {
                    System.out.println("draw black piece");
                    drawBlackPiece(point.y - itemLenght / 2, point.x - itemLenght / 2);
//                    g.setColor(blackColor);
//                    g.fillOval(point.y - itemLenght / 2, point.x - itemLenght / 2, itemLenght, itemLenght);
                } else if (panel[i][j] == -1) {
                    System.out.println("draw white piece");
                    drawWhitePiece(point.y - itemLenght / 2, point.x - itemLenght / 2);
//                    g.setColor(whiteColor);
//                    g.fillOval(point.y - itemLenght / 2, point.x - itemLenght / 2, itemLenght, itemLenght);
                } else {
                    continue;
                }

            }
        }
    }

    /**
     * 重绘一个棋子
     *
     * @param tempX x坐标
     * @param tempY y坐标
     */
    protected void drawRealPiece(int tempX, int tempY) {
        Point p = px2index(tempX, tempY);
        int x = p.x, y = p.y;
        if (panel[y][x] != 0)
            return;
        if (isBlack == true) {
            panel[y][x] = 1;
            drawBlackPiece(tempX, tempY);
        } else {
            panel[y][x] = -1;
            drawWhitePiece(tempX, tempY);
        }
        if (win(x, y) != 0) {
            listener.win(isBlack);
            finish = true;
        }

        isBlack = !isBlack;

    }
    protected void drawRealPieceIndex(int x,int y){
        Point p = index2px(x, y);
        if (panel[y][x] != 0)
            return;
        if (isBlack == true) {
            panel[y][x] = 1;
            drawBlackPiece(p.x-itemLenght/2, p.y - itemLenght/2);
        } else {
            panel[y][x] = -1;
            drawWhitePiece(p.x-itemLenght/2, p.y - itemLenght/2);
        }
        if (win(x, y) != 0) {
            listener.win(isBlack);
            finish = true;
        }

        isBlack = !isBlack;
    }
    public void drawIndexPiece(Point p) {
        p = index2px(p.x, p.y);
        p.x -= itemLenght / 2;
        p.y -= itemLenght / 2;
        drawRealPiece(p.x, p.y);
    }

    /**
     * @param x x坐标
     * @param y y坐标
     * @return 是否已经有棋子
     */
    protected boolean havePiece(int x, int y) {
        Point point = px2index(x, y);
        if (panel[point.y][point.x] != 0)
            return true;
        return false;
    }

    /**
     * 获取标准位置 （itemLenght 的倍数）
     *
     * @param x
     * @param y
     * @return
     */
    protected Point getStandardPx(int x, int y) {
        int tempX = 0, tempY = 0;
        int xmod = x % 50;
        int ymod = y % 50;
        if (xmod < lineLength / 2) {
            tempX = x - xmod - itemLenght / 2;
        } else {
            tempX = x + (lineLength - xmod) - itemLenght / 2;
        }
        if (ymod < lineLength / 2) {
            tempY = y - ymod - itemLenght / 2;
        } else {
            tempY = y + (lineLength - ymod) - itemLenght / 2;
        }
        return new Point(tempX, tempY);
    }

    /**
     * 将实际坐标转换成数组下标
     *
     * @param x 坐标
     * @param y 坐标
     * @return 数组下标 point
     */
    protected Point px2index(int x, int y) {
        Point point = new Point();
        point.x = x / 50;
        point.y = y / 50;
        return point;
    }

    /**
     * 将下标转换为实际坐标
     *
     * @param x 下标
     * @param y 下标
     * @return 实际坐标 point
     */
    protected Point index2px(int x, int y) {
        Point point = new Point();
        point.x = (x + 1) * 50;
        point.y = (y + 1) * 50;
        return point;
    }

    /**
     * 获胜判定
     *
     * @param x x下标
     * @param y y下标
     * @return 1、黑棋 2、-1、白棋 0、进行中
     */
    protected int win(int x, int y) {
        boolean have = checkRow(x, y) || checkCol(x, y) || checkLeftTilt(x, y) || checkRightTilt(x, y);
        if (have) {
            if (isBlack) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    //纵向判断
    protected boolean checkCol(int x, int y) {
        int count = -1;
        int T = isBlack ? 1 : -1;
        for (int i = y; i >= 0; i--) {
            if (panel[i][x] == T)
                count++;
            else
                break;
        }
        for (int i = y; i < lineCount; i++) {
            if (panel[i][x] == T)
                count++;
            else
                break;
        }
        System.out.println("col count = " + count);
        if (count >= MAX_PIECE) {
            return true;
        }
        return false;
    }

    //横向判断
    protected boolean checkRow(int x, int y) {
        int count = -1;
        int T = isBlack ? 1 : -1;
        for (int i = x; i >= 0; i--) {
            if (panel[y][i] == T)
                count++;
            else
                break;
        }
        for (int i = x; i < lineCount; i++) {
            if (panel[y][i] == T)
                count++;
            else
                break;
        }
        System.out.println("row count = " + count);
        if (count >= MAX_PIECE) {
            return true;
        }
        return false;
    }

    //左上判断
    protected boolean checkLeftTilt(int x, int y) {
        int count = -1;
        int T = isBlack ? 1 : -1;
        for (int i = y, j = x; i >= 0 && j >= 0; i--, j--) {
            if (panel[i][j] == T)
                count++;
            else
                break;
        }
        for (int i = y, j = x; i < lineCount && j < lineCount; i++, j++) {
            if (panel[i][j] == T)
                count++;
            else
                break;
        }
        System.out.println("left count = " + count);
        if (count >= MAX_PIECE) {
            return true;
        }
        return false;
    }

    //右上判断
    protected boolean checkRightTilt(int x, int y) {
        int count = -1;
        int T = isBlack ? 1 : -1;
        for (int i = y, j = x; i >= 0 && j < lineCount; i--, j++) {
            if (panel[i][j] == T)
                count++;
            else
                break;
        }
        for (int i = y, j = x; i < lineCount && j >= 0; i++, j--) {
            if (panel[i][j] == T)
                count++;
            else
                break;
        }
        System.out.println("right count = " + count);
        if (count >= MAX_PIECE) {
            return true;
        }
        return false;
    }

    public void begin() {
        finish = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int c = e.getButton();
        System.out.println("x = " + e.getPoint().x + "   y = " + e.getPoint().y) ;
        if (finish) {
            System.out.println("return");
            return;
        }
        //左键按下
        if (c == MouseEvent.BUTTON1) {
            System.out.println("click");
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
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
