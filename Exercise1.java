import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Exercise1 extends JFrame {
    private JLabel countLabel;
    private JLabel diceLabel;
    private JButton rollButton;

    private int totalCount = 0;
    private int totalSum = 0;
    private Random random = new Random();

    public Exercise1() {
        // 設定視窗標題與尺寸
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 開啟時置中
        setLayout(new BorderLayout());

        // 上方：顯示統計資訊
        countLabel = new JLabel("已擲0次,總和0,平均0.00", SwingConstants.CENTER);
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(countLabel, BorderLayout.NORTH);

        // 中央：顯示目前點數
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        diceLabel.setForeground(Color.BLACK);
        add(diceLabel, BorderLayout.CENTER);

        // 下方：擲骰子按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(rollButton, BorderLayout.SOUTH);

        // 按鈕事件處理
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 隨機產生 1-6 的點數
                int point = random.nextInt(6) + 1;

                // 更新統計數據
                totalCount++;
                totalSum += point;
                double average = (double) totalSum / totalCount;

                // 更新上方資訊 (平均值保留兩位小數)
                countLabel.setText(String.format("已擲%d次,總和%d,平均%.2f", totalCount, totalSum, average));

                // 更新中央點數
                diceLabel.setText(String.valueOf(point));

                // 依據點數改變顏色
                if (point == 6) {
                    diceLabel.setForeground(Color.GREEN);
                } else if (point == 1) {
                    diceLabel.setForeground(Color.RED);
                } else {
                    diceLabel.setForeground(Color.BLACK);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Exercise1().setVisible(true);
            }
        });
    }
}