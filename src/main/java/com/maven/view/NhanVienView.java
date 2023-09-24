/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.maven.view;

import com.maven.model.NhanVien;
import com.maven.service.NhanVienService;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author long0
 */
public class NhanVienView extends javax.swing.JFrame {

    NhanVienService nvsv = new NhanVienService();
    DefaultTableModel model;
    private int index = -1;
    DefaultComboBoxModel<String> cbx = new DefaultComboBoxModel<>();

    /**
     * Creates new form NhanVienV
     */
    public NhanVienView() {
        initComponents();
        setLocationRelativeTo(null);
        setCombox();
        fillTable(nvsv.getAll());
        setChu7mau();
        troiChu();
    }

    void fillTable(List<NhanVien> list) {
        model = (DefaultTableModel) jtbNhanVien.getModel();
        model.setRowCount(0);
        for (NhanVien nhanVien : list) {
            model.addRow(nhanVien.toData());
        }
    }

    void setCombox() {
        String[] list = new String[]{" ", "1: Xếp", "2: Nhân Viên"};
        cbx.removeAllElements();
        for (String item : list) {
            cbx.addElement(item);
        }
        cbxVaiTro.setModel(cbx);

    }

    private static final Color[] COLORS = {
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
        Color.BLUE, new Color(75, 0, 130), new Color(148, 0, 211)
    };

    private void setChu7mau() {
        Timer timer = new Timer(1000, new ActionListener() {
            private int colorIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                lbLogo.setForeground(COLORS[colorIndex]);

                colorIndex = (colorIndex + 1) % COLORS.length;
            }
        });
        timer.start();
    }

    void troiChu() {

        Timer timer = new Timer(1, new ActionListener() {
            private int viTriBatDau = 0;  
            private int huongDiChuyen = 1;    

            @Override
            public void actionPerformed(ActionEvent e) {
                updateTextPosition();
            }
            private void updateTextPosition() {
                viTriBatDau += huongDiChuyen;

                if (viTriBatDau <= 0 || viTriBatDau >= jPanelTroi.getWidth() - lbLogo.getWidth()) {
                    huongDiChuyen = -huongDiChuyen;
                }
                lbLogo.setLocation(viTriBatDau, lbLogo.getY());
            }
        });
        timer.start();
    }

    NhanVien readFromNhanVien() {
        String maNV = txtMaNV.getText();
        String matKhau = txtPassword.getText();
        String hoTen = txtHoTen.getText();
        boolean vaitro = false;
        if (cbxVaiTro.getSelectedIndex() == 1) {
            vaitro = true;
        }
        if (cbxVaiTro.getSelectedIndex() == 2) {
            vaitro = false;
        }
        return new NhanVien(maNV, matKhau, hoTen, vaitro);
    }

    boolean validateFromNhanVien() {
        if (txtMaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống");
            txtMaNV.requestFocus();
            return false;
        } else {
            if (txtMaNV.getText().length() > 20) {
                JOptionPane.showMessageDialog(this, "Mã Nhân viên không được quá 20 kí tự ");
                txtMaNV.requestFocus();
                return false;
            }
        }

        if (txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống ");
            txtPassword.requestFocus();
            return false;
        } else {
            if (txtPassword.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không được quá 50 kí tự ");
                txtPassword.requestFocus();
                return false;
            }
        }

        if (txtHoTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            txtHoTen.requestFocus();
            return false;
        } else {
            if (txtHoTen.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Họ tên không được quá 50 kí tự ");
                txtPassword.requestFocus();
                return false;
            }
            if (!(txtHoTen.getText().matches("^[\\p{L}\\s]+$"))) {
                JOptionPane.showMessageDialog(this, "Họ tên sai định dạng ");
                txtHoTen.requestFocus();
                return false;
            }
        }

        if (cbxVaiTro.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn vui lòng chọn vai trò");
            return false;
        }

        return true;
    }

    void showData(int index) {
        NhanVien nv = nvsv.getAll().get(index);
        txtHoTen.setText(nv.getHoTen());
        txtMaNV.setText(nv.getMaNV());
        txtPassword.setText(nv.getMatKhau());
        System.out.println(cbxVaiTro.getSelectedIndex());
        System.out.println(nv.getVaiTro());
        if (nv.getVaiTro() == true) {
            cbxVaiTro.setSelectedIndex(1);
        }
        if (nv.getVaiTro() == false) {
            cbxVaiTro.setSelectedIndex(2);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnXoa = new com.maven.swing.Button();
        btnUpdate = new com.maven.swing.Button();
        btnAdd = new com.maven.swing.Button();
        btnHide = new com.maven.swing.Button();
        txtMaNV = new com.maven.swing.TextField();
        txtHoTen = new com.maven.swing.TextField();
        txtPassword = new com.maven.swing.PasswordField();
        cbxVaiTro = new com.maven.swing.Combobox();
        tableScrollButton1 = new com.maven.table.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbNhanVien = new javax.swing.JTable();
        jPanelTroi = new javax.swing.JPanel();
        lbLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnXoa.setBackground(new java.awt.Color(0, 255, 153));
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 255, 153));
        btnUpdate.setText("Sửa");
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(0, 255, 153));
        btnAdd.setText("Thêm");
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnHide.setBackground(new java.awt.Color(0, 255, 153));
        btnHide.setText("Hide");
        btnHide.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHideActionPerformed(evt);
            }
        });

        txtMaNV.setLabelText("Mã Nhân Viên");

        txtHoTen.setLabelText("Họ Tên");

        txtPassword.setLabelText("Mật Khẩu");

        cbxVaiTro.setLabeText("Vai Trò");
        cbxVaiTro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxVaiTroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHide, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxVaiTro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))
                .addGap(95, 95, 95))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(cbxVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jtbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Mật Khẩu", "Họ Tên", "Vai Trò"
            }
        ));
        jtbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbNhanVienMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtbNhanVien);

        tableScrollButton1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanelTroi.setBackground(new java.awt.Color(255, 255, 255));

        lbLogo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbLogo.setText("QUẢN LÍ NHÂN VIÊN");
        lbLogo.setToolTipText("");

        javax.swing.GroupLayout jPanelTroiLayout = new javax.swing.GroupLayout(jPanelTroi);
        jPanelTroi.setLayout(jPanelTroiLayout);
        jPanelTroiLayout.setHorizontalGroup(
            jPanelTroiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTroiLayout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        jPanelTroiLayout.setVerticalGroup(
            jPanelTroiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTroiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbLogo)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jPanelTroi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTroi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHideActionPerformed
        char echoChar = txtPassword.getEchoChar();
        if (echoChar == '\u0000') {
            txtPassword.setEchoChar('*');
            btnHide.setText("Hiện");
        } else {
            txtPassword.setEchoChar('\u0000');
            btnHide.setText("Ẩn");
        }
    }//GEN-LAST:event_btnHideActionPerformed

    private void jtbNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbNhanVienMouseClicked
        index = jtbNhanVien.getSelectedRow();
        showData(index);

    }//GEN-LAST:event_jtbNhanVienMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        NhanVien nv = this.readFromNhanVien();
        if (validateFromNhanVien()) {
            int jop = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm ? ", "Thêm nhân viên", JOptionPane.YES_NO_OPTION);
            if (jop == 0) {
                if (nvsv.add(nv) != 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    fillTable(nvsv.getAll());
                }
            }

        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void cbxVaiTroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxVaiTroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxVaiTroActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        index = jtbNhanVien.getSelectedRow();
        if (index > 0) {
            NhanVien nvcu = nvsv.getAll().get(index);
            NhanVien nvMoi = this.readFromNhanVien();
            nvMoi.setMaNV(nvcu.getMaNV());
            int jop = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn sửa dữ liệu ? ", "Sửa Nhân Viên", JOptionPane.YES_NO_OPTION);
            if (jop == 0) {
                if (nvsv.update(nvMoi) != 0) {
                    JOptionPane.showMessageDialog(this, "Update thành công");
                    fillTable(nvsv.getAll());
                }
            }

        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        index = jtbNhanVien.getSelectedRow();
        NhanVien nv = nvsv.getAll().get(index);
        int jop = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá nhân viên ? ", "Xoá nhân viên", JOptionPane.YES_NO_OPTION);
        if (jop == 0) {
            if (nvsv.delete(nv) != 0) {
                JOptionPane.showMessageDialog(this, "Xoá thành công");
                fillTable(nvsv.getAll());
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.maven.swing.Button btnAdd;
    private com.maven.swing.Button btnHide;
    private com.maven.swing.Button btnUpdate;
    private com.maven.swing.Button btnXoa;
    private com.maven.swing.Combobox cbxVaiTro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelTroi;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtbNhanVien;
    private javax.swing.JLabel lbLogo;
    private com.maven.table.TableScrollButton tableScrollButton1;
    private com.maven.swing.TextField txtHoTen;
    private com.maven.swing.TextField txtMaNV;
    private com.maven.swing.PasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
