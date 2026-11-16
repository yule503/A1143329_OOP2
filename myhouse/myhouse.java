import java.awt.*;
import javax.swing.*;

public class myhouse extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. 天空與草地：上半部天藍色、下半部綠色 (畫布 500x400)
        g.setColor(new Color(135, 206, 235)); // 天藍色
        g.fillRect(0, 0, 500, 300);

        g.setColor(new Color(60, 179, 113));  // 草地綠色
        g.fillRect(0, 300, 500, 100);

        // 2. 太陽：黃色正圓 + 至少 8 條光芒 (使用 for 迴圈與 Math.cos, Math.sin)
        int sunCenterX = 400;
        int sunCenterY = 75;
        int sunRadius = 35;

        g.setColor(Color.YELLOW);
        g.fillOval(sunCenterX - sunRadius, sunCenterY - sunRadius, sunRadius * 2, sunRadius * 2);

        // 畫 12 條太陽光芒
        int rayCount = 12;
        int innerRadius = 42;
        int outerRadius = 58;
        for (int i = 0; i < rayCount; i++) {
            double angle = 2 * Math.PI * i / rayCount;
            int x1 = (int) (sunCenterX + innerRadius * Math.cos(angle));
            int y1 = (int) (sunCenterY + innerRadius * Math.sin(angle));
            int x2 = (int) (sunCenterX + outerRadius * Math.cos(angle));
            int y2 = (int) (sunCenterY + outerRadius * Math.sin(angle));
            g.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵：至少 3 個互相重疊的白色 fillOval
        g.setColor(Color.WHITE);
        g.fillOval(80, 75, 70, 45);
        g.fillOval(115, 60, 85, 55);
        g.fillOval(165, 75, 70, 45);

        // 4. 房屋主體：fillRect + drawRect
        int houseX = 160;
        int houseY = 200;
        int houseW = 180;
        int houseH = 130;

        g.setColor(new Color(245, 222, 179)); // 淺米色牆壁
        g.fillRect(houseX, houseY, houseW, houseH);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(houseX, houseY, houseW, houseH);

        // 5. 屋頂：只能用 drawLine 畫出三角形（三條線段）
        g.setColor(Color.RED);
        int topX = 250, topY = 125;     // 屋頂頂點
        int leftX = 145, leftY = 200;   // 屋簷左端
        int rightX = 355, rightY = 200; // 屋簷右端

        g.drawLine(topX, topY, leftX, leftY);     // 左斜邊
        g.drawLine(topX, topY, rightX, rightY);   // 右斜邊
        g.drawLine(leftX, leftY, rightX, rightY); // 底邊

        // 6. 門：fillRoundRect + 小的 fillOval 當門把
        int doorX = 232;
        int doorY = 260;
        int doorW = 36;
        int doorH = 70;

        g.setColor(new Color(139, 69, 19)); // 咖啡色木門
        g.fillRoundRect(doorX, doorY, doorW, doorH, 10, 10);

        g.setColor(Color.YELLOW); // 金黃色門把
        g.fillOval(doorX + 26, doorY + 35, 6, 6);

        // 7. 窗戶：至少 2 扇，用 fillRect + drawRect，並用 drawLine 畫十字窗框
        drawWindow(g, 180, 225, 38, 38); // 左窗
        drawWindow(g, 282, 225, 38, 38); // 右窗

        // 8. 簽名：在畫面角落簽名（請自行修改為你的學號與姓名）
        g.setColor(Color.BLACK);
        g.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        g.drawString("A1143329 廖語婕", 15, 385);
    }

    // 輔助方法：繪製單扇窗戶與十字窗框
    private void drawWindow(Graphics g, int x, int y, int w, int h) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, w, h);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
        g.drawLine(x + w / 2, y, x + w / 2, y + h); // 垂直窗框
        g.drawLine(x, y + h / 2, x + w, y + h / 2); // 水平窗框
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("My House");
        myhouse panel = new myhouse();

        // 設定畫布內容區大小為 500x400
        panel.setPreferredSize(new Dimension(500, 400));

        frame.add(panel);
        frame.pack(); // 自動依據 panel 大小調整視窗
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 視窗置中顯示
        frame.setVisible(true);
    }
}