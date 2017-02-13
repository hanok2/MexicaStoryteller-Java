package mexica.action.gui;

import javax.swing.DefaultComboBoxModel;
import mexica.core.*;

/**
 *
 * @author ivang_000
 */
public class TensionPanel extends javax.swing.JPanel {
    private Condition condition;
    /**
     * Creates new form TensionPanel
     */
    public TensionPanel() {
        initComponents();
        loadTensions();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbTensionType = new javax.swing.JComboBox<TensionType>();
        jLabel3 = new javax.swing.JLabel();
        cbPerformer = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cbReceiver = new javax.swing.JComboBox();
        cbPresenceConditioned = new javax.swing.JCheckBox();

        jLabel1.setText("Tension type:");

        jLabel3.setText("Performer:");

        cbPerformer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "a", "b", "La", "Lb", "any" }));

        jLabel4.setText("Receiver:");

        cbReceiver.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "a", "b", "La", "Lb", "any", "" }));

        cbPresenceConditioned.setText("Presence conditioned");
        cbPresenceConditioned.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cbPresenceConditioned.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbPresenceConditioned)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(cbTensionType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(32, 32, 32)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cbPerformer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbReceiver, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbTensionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbPerformer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbReceiver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cbPresenceConditioned)
                .addContainerGap(11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbPerformer;
    private javax.swing.JCheckBox cbPresenceConditioned;
    private javax.swing.JComboBox cbReceiver;
    private javax.swing.JComboBox<TensionType> cbTensionType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
    /**
     * @return the condition
     */
    public Condition getCondition() {
        if (condition == null)
            condition = new Condition();
        condition.setConditionType(ConditionType.Tension);
        condition.setTensionType((TensionType)cbTensionType.getSelectedItem());
        condition.setCharacterA((String)cbPerformer.getSelectedItem());
        //If the selected value is empty space, do not store anything
        if (cbReceiver.getSelectedIndex() < cbReceiver.getModel().getSize() - 1)
            condition.setCharacterB((String)cbReceiver.getSelectedItem());
        condition.setPresenceConditioned(cbPresenceConditioned.isSelected());
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
        if (condition != null) {
            cbTensionType.setSelectedItem(condition.getTension());
            cbPerformer.setSelectedItem(condition.getCharacterA());
            if (condition.getCharacterB() != null)
                cbReceiver.setSelectedItem(condition.getCharacterB());
            else
                cbReceiver.setSelectedIndex(cbReceiver.getModel().getSize()-1);
            cbPresenceConditioned.setSelected(condition.isPresenceConditionedTension());
        }
    }

    private void loadTensions() {
        DefaultComboBoxModel<TensionType> model = new DefaultComboBoxModel<>();
        for (TensionType e : TensionType.values()) {
            model.addElement(e);
        }
        cbTensionType.setModel(model);
    }
}