package ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AI {

    private boolean isBlack;
    private int lineCount = 15;
    private int T = 1;

    public AI() {

    }

    public Point simple(int[][] panel, Point last) {
        int x = last.x;
        int y = last.y;
        int max = -1;
        int which = -1;
        int[] old = new int[4];
        int[] order = new int[4];
        Holder[] holders = new Holder[4];
        holders[0] = checkCol(panel, x, y);
        order[0] = holders[0].count;
        holders[1] = checkRow(panel, x, y);
        order[1] = holders[1].count;
        holders[2] = checkLeftTilt(panel, x, y);
        order[2] = holders[2].count;
        holders[3] = checkRightTilt(panel, x, y);
        order[3] = holders[3].count;
        System.arraycopy(order, 0, old, 0, 4);
        Arrays.sort(order);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                if (holders[i].points[j] != null)
                    System.out.println(j + " " + holders[i].points[j]);
                else
                    System.out.println("null");
            }
        }
        Point p = null;
        for (int i = 3; i >= 0; i--) {
            int j = -1;
            for (j = 0; j < 4; j++) {
                if (order[i] == old[j])
                    break;
            }
            p = holders[j].points[0];
            if (p != null && panel[p.y][p.x] == 0) {
                break;
            }
            p = holders[j].points[1];
            if (p != null && panel[p.y][p.x] == 0) {
                break;
            }
        }
        return p;
    }

    //Holder
    private Holder checkCol(int[][] panel, int x, int y) {
        int count = -1;
        Point[] points = new Point[2];
        for (int i = y; i >= 0; i--) {
            if (panel[i][x] == T)
                count++;
            else {
                points[0] = new Point(x, i);
                break;
            }
        }
        for (int i = y; i < lineCount; i++) {
            if (panel[i][x] == T)
                count++;
            else {
                points[1] = new Point(x, i);
                break;
            }
        }
        return new Holder(points, count);
    }

    //横向判断
    protected Holder checkRow(int[][] panel, int x, int y) {
        int count = -1;
        Point[] points = new Point[2];
        for (int i = x; i >= 0; i--) {
            if (panel[y][i] == T)
                count++;
            else {
                points[0] = new Point(i, y);
                break;
            }
        }
        for (int i = x; i < lineCount; i++) {
            if (panel[y][i] == T)
                count++;
            else {
                points[1] = new Point(i, y);
                break;
            }
        }
        return new Holder(points, count);
    }

    //左上判断
    protected Holder checkLeftTilt(int[][] panel, int x, int y) {
        int count = -1;
        Point[] points = new Point[2];
        for (int i = y, j = x; i >= 0 && j >= 0; i--, j--) {
            if (panel[i][j] == T)
                count++;
            else {
                points[0] = new Point(j, i);
                break;
            }
        }
        for (int i = y, j = x; i < lineCount && j < lineCount; i++, j++) {
            if (panel[i][j] == T)
                count++;
            else {
                points[1] = new Point(j, i);
                break;
            }
        }
        return new Holder(points, count);
    }

    //右上判断
    protected Holder checkRightTilt(int[][] panel, int x, int y) {
        int count = -1;
        Point[] points = new Point[2];
        for (int i = y, j = x; i >= 0 && j < lineCount; i--, j++) {
            if (panel[i][j] == T)
                count++;
            else {
                points[0] =  new Point(j, i);
                break;
            }
        }
        for (int i = y, j = x; i < lineCount && j >= 0; i++, j--) {
            if (panel[i][j] == T)
                count++;
            else {
                points[1] = new Point(j, i);
                break;
            }
        }
        return new Holder(points, count);
    }

    private class Holder implements Comparable {
        public Holder(Point[] points, int count) {
            this.points = points;
            this.count = count;
        }

        public Point[] points = new Point[2];
        public int count = 0;

        @Override
        public int compareTo(Object o) {
            return this.count > ((Holder) o).count ? 1 : 0;
        }
    }
}
