import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Exercise4 extends JFrame {
    // UI 元件
    private JLabel countLabel;
    private JLabel diceLabel;
    private JLabel freqLabel;
    private JTextArea historyArea;
    private JButton rollButton;
    private JButton resetButton;

    // 數據統計變數
    private int totalCount = 0;
    private int totalSum = 0;
    private int[] pointCounts = new int[7]; // 索引 1~6 分別記錄各點數出現次數
    private Random random = new Random();

    public Exercise4() {
        // 1. 視窗基本設定
        setTitle("骰子模擬器 Pro");
        setSize(520, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 2. 上方：總次數、總和、平均統計
        countLabel = new JLabel("已擲 0 次 | 總和 0 | 平均 0.00", SwingConstants.CENTER);
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        countLabel.setBorder(new EmptyBorder(12, 10, 5, 10));
        add(countLabel, BorderLayout.NORTH);

        // 3. 中央：顯示目前骰子點數
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 80));
        diceLabel.setForeground(Color.DARK_GRAY);
        diceLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(diceLabel, BorderLayout.CENTER);

        // 4. 右側：歷史紀錄面板
        historyArea = new JTextArea(10, 14);
        historyArea.setEditable(false); // 禁止使用者手動修改紀錄
        historyArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("擲骰紀錄"));
        add(scrollPane, BorderLayout.EAST);

        // 5. 下方：各點數統計 + 按鈕區
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 8));
        bottomPanel.setBorder(new EmptyBorder(5, 12, 12, 12));

        freqLabel = new JLabel("點數統計：1點(0)  2點(0)  3點(0)  4點(0)  5點(0)  6點(0)", SwingConstants.CENTER);
        freqLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        bottomPanel.add(freqLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        resetButton = new JButton("重置");
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 16));

        buttonPanel.add(rollButton);
        buttonPanel.add(resetButton);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // 6. 綁定事件
        rollButton.addActionListener(e -> startRollAnimation());
        resetButton.addActionListener(e -> resetGame());
    }

    /**
     * 啟動擲骰動畫，結束後結算最終點數
     */
    private void startRollAnimation() {
        rollButton.setEnabled(false); // 動畫期間鎖定按鈕
        resetButton.setEnabled(false);

        final int[] ticks = {0}; // 記錄動畫跳動次數
        Timer timer = new Timer(50, null); // 每 50 毫秒觸發一次
        timer.addActionListener(e -> {
            // 顯示隨機假數字製造滾動感
            int fakePoint = random.nextInt(6) + 1;
            diceLabel.setText(String.valueOf(fakePoint));
            diceLabel.setForeground(Color.GRAY);
            ticks[0]++;

            // 跳動 10 次 (約 0.5 秒) 後停止，結算真正結果
            if (ticks[0] >= 10) {
                timer.stop();
                finalizeRoll();
                rollButton.setEnabled(true);
                resetButton.setEnabled(true);
            }
        });
        timer.start();
    }

    /**
     * 產生真正點數並更新所有畫面與統計
     */
    private void finalizeRoll() {
        int point = random.nextInt(6) + 1;

        // 更新數據
        totalCount++;
        totalSum += point;
        pointCounts[point]++;
        double average = (double) totalSum / totalCount;

        // 更新上方總計與中央數字
        countLabel.setText(String.format("已擲 %d 次 | 總和 %d | 平均 %.2f", totalCount, totalSum, average));
        diceLabel.setText(String.valueOf(point));

        // 依據點數設定顏色
        if (point == 6) {
            diceLabel.setForeground(new Color(0, 153, 51)); // 柔和深綠色
        } else if (point == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }

        // 更新下方各點數出現次數
        freqLabel.setText(String.format(
            "點數統計：1點(%d)  2點(%d)  3點(%d)  4點(%d)  5點(%d)  6點(%d)",
            pointCounts[1], pointCounts[2], pointCounts[3],
            pointCounts[4], pointCounts[5], pointCounts[6]
        ));

        // 新增歷史紀錄並自動捲動至最底部
        historyArea.append(String.format("第 %d 次：擲出 %d 點\n", totalCount, point));
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }

    /**
     * 重置所有數據與畫面
     */
    private void resetGame() {
        totalCount = 0;
        totalSum = 0;
        pointCounts = new int[7];

        countLabel.setText("已擲 0 次 | 總和 0 | 平均 0.00");
        diceLabel.setText("-");
        diceLabel.setForeground(Color.DARK_GRAY);
        freqLabel.setText("點數統計：1點(0)  2點(0)  3點(0)  4點(0)  5點(0)  6點(0)");
        historyArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercise4().setVisible(true));
    }
}