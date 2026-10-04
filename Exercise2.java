import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Exercise2 extends JFrame {
    // 畫面元件
    private JComboBox<String> typeComboBox;
    private JComboBox<String> fromUnitComboBox;
    private JComboBox<String> toUnitComboBox;
    private JTextField inputField;
    private JTextField outputField;
    private JButton convertButton;

    // 各類別對應的單位選項
    private final String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private final String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private final String[] tempUnits = {"攝氏", "華氏", "克氏"};

    public Exercise2() {
        // 設定視窗基本規格：標題與大小 (480x280)
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 畫面居中
        setLayout(new BorderLayout(10, 10)); // 使用 BorderLayout

        // ---------------- NORTH 區域 ----------------
        JPanel northPanel = new JPanel();
        typeComboBox = new JComboBox<>(new String[]{"長度", "重量", "溫度"});
        northPanel.add(new JLabel("換算類型："));
        northPanel.add(typeComboBox);
        add(northPanel, BorderLayout.NORTH);

        // ---------------- CENTER 區域 ----------------
        // 使用 GridLayout(2, 1) 排版兩列
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 第一列：輸入框 + 來源單位JComboBox
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        inputField = new JTextField(12);
        fromUnitComboBox = new JComboBox<>();
        row1.add(new JLabel("數值："));
        row1.add(inputField);
        row1.add(fromUnitComboBox);

        // 第二列：輸出框 + 目標單位JComboBox
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        outputField = new JTextField(12);
        outputField.setEditable(false); // 設定結果顯示框不可直接編輯
        toUnitComboBox = new JComboBox<>();
        row2.add(new JLabel("結果："));
        row2.add(outputField);
        row2.add(toUnitComboBox);

        centerPanel.add(row1);
        centerPanel.add(row2);
        add(centerPanel, BorderLayout.CENTER);

        // ---------------- SOUTH 區域 ----------------
        JPanel southPanel = new JPanel();
        convertButton = new JButton("換算");
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // ---------------- 事件監聽設定 ----------------
        
        // 預設載入「長度」選項
        updateUnitComboBoxes("長度");

        // 1. 切換換算類型事件：動態變更單位JComboBox選項
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) typeComboBox.getSelectedItem();
                updateUnitComboBoxes(selectedType);
            }
        });

        // 2. 按下「換算」按鈕事件
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    /**
     * 根據選擇的換算類型更新來源與目標單位的 JComboBox
     */
    private void updateUnitComboBoxes(String type) {
        fromUnitComboBox.removeAllItems();
        toUnitComboBox.removeAllItems();

        String[] units;
        switch (type) {
            case "重量":
                units = weightUnits;
                break;
            case "溫度":
                units = tempUnits;
                break;
            case "長度":
            default:
                units = lengthUnits;
                break;
        }

        for (String unit : units) {
            fromUnitComboBox.addItem(unit);
            toUnitComboBox.addItem(unit);
        }
        
        // 預設第二個選單選不同項目，提升使用者體驗
        if (toUnitComboBox.getItemCount() > 1) {
            toUnitComboBox.setSelectedIndex(1);
        }
    }

    /**
     * 執行單位換算邏輯
     */
    private void performConversion() {
        String inputText = inputField.getText().trim();
        if (inputText.isEmpty()) {
            outputField.setText("請輸入數值");
            return;
        }

        double val;
        try {
            val = Double.parseDouble(inputText);
        } catch (NumberFormatException ex) {
            outputField.setText("請輸入有效數字");
            return;
        }

        String type = (String) typeComboBox.getSelectedItem();
        String from = (String) fromUnitComboBox.getSelectedItem();
        String to = (String) toUnitComboBox.getSelectedItem();

        if (from == null || to == null) return;

        double result = 0;

        switch (type) {
            case "長度":
                result = convertLength(val, from, to);
                break;
            case "重量":
                result = convertWeight(val, from, to);
                break;
            case "溫度":
                result = convertTemperature(val, from, to);
                break;
        }

        // 格式化輸出（保留小數點後 4 位）
        outputField.setText(String.format("%.4f", result));
    }

    // 長度換算：統一先轉為「公尺」
    private double convertLength(double val, String from, String to) {
        double meters = 0;
        switch (from) {
            case "公尺": meters = val; break;
            case "公分": meters = val / 100.0; break;
            case "英吋": meters = val * 0.0254; break;
            case "英尺": meters = val * 0.3048; break;
        }

        switch (to) {
            case "公尺": return meters;
            case "公分": return meters * 100.0;
            case "英吋": return meters / 0.0254;
            case "英尺": return meters / 0.3048;
            default: return 0;
        }
    }

    // 重量換算：統一先轉為「公斤」
    private double convertWeight(double val, String from, String to) {
        double kg = 0;
        switch (from) {
            case "公斤": kg = val; break;
            case "公克": kg = val / 1000.0; break;
            case "磅":   kg = val * 0.45359237; break;
            case "盎司": kg = val * 0.028349523125; break;
        }

        switch (to) {
            case "公斤": return kg;
            case "公克": return kg * 1000.0;
            case "磅":   return kg / 0.45359237;
            case "盎司": return kg / 0.028349523125;
            default: return 0;
        }
    }

    // 溫度換算：先轉為「攝氏」再轉為目標單位
    private double convertTemperature(double val, String from, String to) {
        double celsius = 0;
        switch (from) {
            case "攝氏": celsius = val; break;
            case "華氏": celsius = (val - 32) * 5.0 / 9.0; break;
            case "克氏": celsius = val - 273.15; break;
        }

        switch (to) {
            case "攝氏": return celsius;
            case "華氏": return (celsius * 9.0 / 5.0) + 32;
            case "克氏": return celsius + 273.15;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        // 在 Event Dispatch Thread 中啟動 GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void actionPerformed() { // 若編譯器報錯，可改成 run()
            }
            public void run() {
                new Exercise2().setVisible(true);
            }
        });
    }
}