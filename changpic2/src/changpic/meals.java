package changpic;

import java.io.Serializable;
import java.time.LocalDateTime;

public class  meals extends order implements Serializable {

    private String mealName;
    private float singlMealPrice;
    private int SingleMealPrepTime;
    private int mealCount=0;
    private String imagePath;

 public meals()
 {
 super.setTotalOrderPrice(singlMealPrice);

 }

 public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getSinglMealPrice() {
        return singlMealPrice;
    }

    public void setSinglMealPrice(float singlMealPrice) {
        this.singlMealPrice = singlMealPrice;
    }

    public int getSingleMealPrepTime() {
        return SingleMealPrepTime;
    }

    public void setSingleMealPrepTime(int SingleMealPrepTime) {
        this.SingleMealPrepTime = SingleMealPrepTime;
    }



    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return " Meal Name : " + mealName +"";
    }

}

/*

    private void viewOrder(clients object) {
        JFrame orderFrame = new JFrame("Your Order");
        orderFrame.setSize(400, 500);
        orderFrame.setLayout(new BorderLayout(10, 10));

        // إنشاء قائمة الطلبات
        JList<meals> orderList = new JList<>(object.getMealsClientList());
        JScrollPane scrollPane = new JScrollPane(orderList);
        orderFrame.add(scrollPane, BorderLayout.CENTER);

        // إنشاء لوحة معلومات الطلب
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 10, 10)); // تخطيط شبكي

        // إضافة حقول المعلومات
        infoPanel.add(new JLabel("نوع الوجبة:"));
        JTextField mealTypeField = new JTextField();
        mealTypeField.setEditable(false); // غير قابل للتعديل
        infoPanel.add(mealTypeField);

        infoPanel.add(new JLabel("عدد تكرار الوجبة:"));
        JTextField mealCountField = new JTextField();
        mealCountField.setEditable(false);
        infoPanel.add(mealCountField);

        infoPanel.add(new JLabel("سعر الوجبة الواحدة:"));
        JTextField mealPriceField = new JTextField();
        mealPriceField.setEditable(false);
        infoPanel.add(mealPriceField);

        infoPanel.add(new JLabel("سعر المجموع الكلي:"));
        JTextField totalPriceField = new JTextField();
        totalPriceField.setEditable(false);
        infoPanel.add(totalPriceField);

        infoPanel.add(new JLabel("وقت تحضير الوجبة الواحدة:"));
        JTextField singleMealPrepTimeField = new JTextField();
        singleMealPrepTimeField.setEditable(false);
        infoPanel.add(singleMealPrepTimeField);

        infoPanel.add(new JLabel("وقت تحضير جميع الوجبات:"));
        JTextField totalMealsPrepTimeField = new JTextField();
        totalMealsPrepTimeField.setEditable(false);
        infoPanel.add(totalMealsPrepTimeField);

        infoPanel.add(new JLabel("وقت تحضير الطلب بالكامل:"));
        JTextField totalOrderPrepTimeField = new JTextField();
        totalOrderPrepTimeField.setEditable(false);
        infoPanel.add(totalOrderPrepTimeField);

        infoPanel.add(new JLabel("سعر الطلب الكلي:"));
        JTextField totalOrderPriceField = new JTextField();
        totalOrderPriceField.setEditable(false);
        infoPanel.add(totalOrderPriceField);

        // إضافة خانة تاريخ الطلب
        infoPanel.add(new JLabel("تاريخ الطلب:"));
        JTextField orderDateField = new JTextField();
        orderDateField.setEditable(false);
        infoPanel.add(orderDateField);

        // إضافة لوحة المعلومات إلى الفريم
        orderFrame.add(infoPanel, BorderLayout.NORTH);

        // إعداد زر الإغلاق
        JButton closeButton = new JButton("Close");
        styleSmallButton(closeButton);
        closeButton.addActionListener(e -> orderFrame.dispose());

        orderFrame.add(closeButton, BorderLayout.SOUTH);

        // تحديث الحقول بالمعلومات المناسبة من كائن الطلب
        updateOrderDetails(object, mealTypeField, mealCountField, mealPriceField, totalPriceField,
                singleMealPrepTimeField, totalMealsPrepTimeField, totalOrderPrepTimeField,
                totalOrderPriceField, orderDateField);

        orderFrame.setLocationRelativeTo(frame);
        orderFrame.setVisible(true);
    }

 */
