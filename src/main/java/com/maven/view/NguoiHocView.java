/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.maven.view;

import com.maven.model.NguoiHoc;
import com.maven.service.NguoiHocService;
import com.maven.swing.EventPagination;
import com.maven.swing.PaginationItemRender;
import com.maven.swing.PaginationItemRenderStyle1;
import com.maven.until.HibernateUtil;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author long0
 */
public class NguoiHocView extends javax.swing.JFrame {

    DefaultTableModel model;
    List<NguoiHoc> nguoiHocList = null;
    private int index = -1;
    NguoiHocService nhsv = new NguoiHocService();

    /**
     * Creates new form NguoiHocView
     */
    public NguoiHocView() {
        initComponents();
        setLocationRelativeTo(null);
        getContentPane().setBackground(java.awt.Color.WHITE);
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadData(page);
            }
        });
        troiChu();
        setChu7mau();

    }

    private void exportToExcel(List<NguoiHoc> nguoiHocList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("NguoiHocData");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã người học");
        headerRow.createCell(1).setCellValue("Mã Nhân Viên");
        headerRow.createCell(2).setCellValue("Giới tính");
        headerRow.createCell(3).setCellValue("Ngày Sinh");
        headerRow.createCell(4).setCellValue("Email");
        headerRow.createCell(5).setCellValue("Mật Khẩu");
        headerRow.createCell(6).setCellValue("Điện thoại");
        headerRow.createCell(7).setCellValue("Ghi chú");
        headerRow.createCell(8).setCellValue("Ngày đăng kí");

        int numRow = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        for (NguoiHoc nguoiHoc : nguoiHocList) {
            Row row = sheet.createRow(numRow++);
            row.createCell(0).setCellValue(nguoiHoc.getMaNH());
            row.createCell(1).setCellValue(nguoiHoc.getMaNV());
            row.createCell(2).setCellValue(nguoiHoc.getGioiTinh());

            // Định dạng ngày sinh
            Cell ngaySinhCell = row.createCell(3);
            ngaySinhCell.setCellValue(nguoiHoc.getNgaySinh());
            ngaySinhCell.setCellStyle(dateCellStyle);

            row.createCell(4).setCellValue(nguoiHoc.getEmail());
            row.createCell(5).setCellValue(nguoiHoc.getMatKhau());
            row.createCell(6).setCellValue(nguoiHoc.getDienThoai());
            row.createCell(7).setCellValue(nguoiHoc.getGhiChu());

            // Định dạng ngày đăng kí
            Cell ngayDKCell = row.createCell(8);
            ngayDKCell.setCellValue(nguoiHoc.getNgayDK());
            ngayDKCell.setCellStyle(dateCellStyle);
        }
        sheet.setColumnWidth(0, 4000); // Mã người học
        sheet.setColumnWidth(1, 4000); // Mã Nhân Viên
        sheet.setColumnWidth(2, 2000); // Giới tính
        sheet.setColumnWidth(3, 3000); // Ngày Sinh
        sheet.setColumnWidth(4, 6000); // Email
        sheet.setColumnWidth(5, 5000); // Mật Khẩu
        sheet.setColumnWidth(6, 4000); // Điện thoại
        sheet.setColumnWidth(7, 6000); // Ghi chú
        sheet.setColumnWidth(8, 4000); // Ngày đăng kí

        try ( FileOutputStream out = new FileOutputStream("nguoihocdata.xlsx")) {
            workbook.write(out);
            System.out.println("Xuất file thành công");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Xuất file thất bại");
        }

        try {
            File file = new File("nguoihocdata.xlsx");
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không thể mở file");
        }
    }

    void fillTable(List<NguoiHoc> list) {
        model.setRowCount(0);
        for (NguoiHoc nguoiHoc : nguoiHocList) {
            model.addRow(nguoiHoc.toData());
        }
    }

    NguoiHoc readForm() {
        String maNguoiHoc = txtMaNguoiHoc.getText();
        String matKhau = txtMatKhau.getText();
        boolean gioiTinh;
        if (rdNam.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = true;
        }
        Date ngaySinh = txtNgaySinh.getDate();
        String email = txtEmail.getText();
        String dienThoai = txtSoDienThoai.getText();
        String ghiChu = txtGhiChu.getText();
        String maNV = txtMaNhanVien.getText();
        Date ngayDK = txtNgayDangKi.getDate();
        return new NguoiHoc(maNguoiHoc, matKhau, gioiTinh, ngaySinh, email, dienThoai, ghiChu, maNV, ngayDK);
    }

    boolean validateData() {
        if (txtMaNguoiHoc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã người học không được để trống");
            txtMaNguoiHoc.requestFocus();
            return false;
        } else {
            if (txtMaNguoiHoc.getText().length() > 7) {
                JOptionPane.showMessageDialog(this, "Mã người học không quá 7 kí tự");
                txtMaNguoiHoc.requestFocus();
                return false;
            }
        }

        if (txtMaNhanVien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống");
            txtMaNhanVien.requestFocus();
            return false;
        } else {
            if (txtMaNhanVien.getText().length() > 20) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không được quá 20 kí tự");
                txtMaNhanVien.requestFocus();
                return false;
            }
        }

        if (txtMatKhau.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            txtMatKhau.requestFocus();
            return false;
        } else {
            if (txtMatKhau.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không quá 50 kí tự");
                txtMatKhau.requestFocus();
                return false;
            }
        }

        if (txtNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ngày sinh");
            return false;
        }

        if (txtNgayDangKi.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ngày đăng kí");
            return false;
        }

        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            txtEmail.requestFocus();
            return false;
        } else {
            if (!(txtEmail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
                JOptionPane.showMessageDialog(this, "Email sai định dạng");
                txtEmail.requestFocus();
                return false;
            }
            if (txtEmail.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Email không quá 50 kí tự");
                txtEmail.requestFocus();
                return false;
            }
        }
        if (txtSoDienThoai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại  không được để trống");
            txtSoDienThoai.requestFocus();
            return false;
        } else {
            if (!(txtSoDienThoai.getText().matches("^\\+?[0-9\\s\\-]+$"))) {
                JOptionPane.showMessageDialog(this, "Số điện thoại sai định dạng");
                txtSoDienThoai.requestFocus();
                return false;
            }
            if (txtSoDienThoai.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được quá 10 số thứ tự");
                txtSoDienThoai.requestFocus();
                return false;
            }
        }

        if (txtGhiChu.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ghi chú không được để trống");
            txtGhiChu.requestFocus();
            return false;
        } else {
            if (txtGhiChu.getText().length() > 225) {
                JOptionPane.showMessageDialog(this, "Ghi chú không được quá 225 kí tự");
                txtGhiChu.requestFocus();
                return false;
            }
        }

        return true;
    }

    void showData(int index) {
        NguoiHoc nh = nguoiHocList.get(index);
        txtMaNguoiHoc.setText(nh.getMaNH());
        txtEmail.setText(nh.getEmail());
        txtGhiChu.setText(nh.getGhiChu());
        txtMaNhanVien.setText(nh.getMaNV());
        txtSoDienThoai.setText(nh.getDienThoai());
        txtNgaySinh.setDate(nh.getNgaySinh());
        txtNgayDangKi.setDate(nh.getNgayDK());
        txtMatKhau.setText(nh.getMatKhau());
        if (nh.getGioiTinh()) {
            rdNam.setSelected(true);
        } else {
            rdNu.setSelected(true);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        textArea1 = new com.maven.swing.TextArea();
        jPanel1 = new javax.swing.JPanel();
        txtMaNguoiHoc = new com.maven.swing.TextField();
        txtEmail = new com.maven.swing.TextField();
        rdNam = new com.maven.swing.RadioButtonCustom();
        jLabel4 = new javax.swing.JLabel();
        rdNu = new com.maven.swing.RadioButtonCustom();
        txtMatKhau = new com.maven.swing.PasswordField();
        btnHide = new com.maven.swing.Button();
        jLabel5 = new javax.swing.JLabel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        txtSoDienThoai = new com.maven.swing.TextField();
        txtNgayDangKi = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        textAreaScroll1 = new com.maven.swing.TextAreaScroll();
        txtGhiChu = new com.maven.swing.TextArea();
        txtMaNhanVien = new com.maven.swing.TextField();
        btnThem = new com.maven.swing.Button();
        btnSua = new com.maven.swing.Button();
        btnXoa = new com.maven.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbNguoiHoc = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        pagination1 = new com.maven.swing.Pagination();
        btnXoa1 = new com.maven.swing.Button();
        jPanelTroi = new javax.swing.JPanel();
        lbLogo = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÍ NHÂN VIÊN");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("QUẢN LÍ NHÂN VIÊN");
        jLabel2.setToolTipText("");

        textArea1.setColumns(20);
        textArea1.setRows(5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtMaNguoiHoc.setLabelText("Mã người học");

        txtEmail.setLabelText("Email");
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        rdNam.setBackground(new java.awt.Color(255, 204, 0));
        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");
        rdNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamActionPerformed(evt);
            }
        });

        jLabel4.setText("Giới Tính ");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        txtMatKhau.setLabelText("Mật Khẩu");

        btnHide.setBackground(new java.awt.Color(0, 255, 153));
        btnHide.setText("Hide");
        btnHide.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHideActionPerformed(evt);
            }
        });

        jLabel5.setText("Ngày Sinh");

        txtNgaySinh.setDateFormatString("dd-MM-yyyy");

        txtSoDienThoai.setLabelText("Điện thoại");

        txtNgayDangKi.setDateFormatString("dd-MM-yyyy");

        jLabel6.setText("Ngày Đăng Kí");

        textAreaScroll1.setLabelText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        textAreaScroll1.setViewportView(txtGhiChu);

        txtMaNhanVien.setLabelText("Mã nhân viên");

        btnThem.setBackground(new java.awt.Color(0, 255, 153));
        btnThem.setText("Thêm ");
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 255, 153));
        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 255, 153));
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jtbNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NH", "Mã NV", "Giới tính", "Ngày sinh", "Email", "Mật khẩu", "Điện thoại", "Ghi Chú", "Ngày ĐK"
            }
        ));
        jtbNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbNguoiHoc);
        if (jtbNguoiHoc.getColumnModel().getColumnCount() > 0) {
            jtbNguoiHoc.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        pagination1.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(519, 519, 519))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnXoa1.setBackground(new java.awt.Color(0, 255, 153));
        btnXoa1.setText("Xuất Exel");
        btnXoa1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        jPanelTroi.setBackground(new java.awt.Color(255, 255, 255));

        lbLogo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbLogo.setText("QUẢN LÍ NGƯỜI HỌC");

        javax.swing.GroupLayout jPanelTroiLayout = new javax.swing.GroupLayout(jPanelTroi);
        jPanelTroi.setLayout(jPanelTroiLayout);
        jPanelTroiLayout.setHorizontalGroup(
            jPanelTroiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTroiLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(lbLogo)
                .addGap(53, 53, 53))
        );
        jPanelTroiLayout.setVerticalGroup(
            jPanelTroiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTroiLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbLogo)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNgayDangKi, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel5)
                                                    .addGap(24, 24, 24))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel4)
                                                    .addGap(28, 28, 28)))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                    .addGap(12, 12, 12)
                                                    .addComponent(rdNam, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(71, 71, 71)
                                                    .addComponent(rdNu, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGap(96, 96, 96))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(btnHide, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(313, 313, 313)
                                .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(462, 462, 462)
                        .addComponent(jPanelTroi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelTroi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(rdNu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel6))
                                    .addComponent(txtNgayDangKi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(textAreaScroll1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadData(int page) {
        int limit = 10;
        int offset = (page - 1) * limit;

        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            DecimalFormat f = new DecimalFormat("#,##0.##");
            model = (DefaultTableModel) jtbNguoiHoc.getModel();
            model.setRowCount(0);
            int rowCount = nhsv.rowCount();
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            nguoiHocList = nhsv.listPhanTrang(limit, offset);
            fillTable(nhsv.getAll());
            pagination1.setPagegination(page, totalPages);
            System.out.println("Tổng số trang: " + totalPages);
            System.out.println("Trang hiện tại: " + page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final java.awt.Color[] COLORS = {
        java.awt.Color.RED, java.awt.Color.ORANGE, java.awt.Color.YELLOW, java.awt.Color.GREEN,
        java.awt.Color.BLUE, new java.awt.Color(75, 0, 130), new java.awt.Color(148, 0, 211)
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


    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        loadData(1);
    }//GEN-LAST:event_formWindowOpened

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        exportToExcel(nhsv.getAll());
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void jtbNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbNguoiHocMouseClicked
        index = jtbNguoiHoc.getSelectedRow();
        showData(index);
    }//GEN-LAST:event_jtbNguoiHocMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        NguoiHoc nh = this.readForm();
        if (validateData()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm người học ? ", "Thêm người học", JOptionPane.YES_NO_OPTION);
            if (confirm == 0) {
                if (nhsv.add(nh) != 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    loadData(1);
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHideActionPerformed
        char echoChar = txtMatKhau.getEchoChar();
        if (echoChar == '\u0000') {
            txtMatKhau.setEchoChar('*');
            btnHide.setText("Hiện");
        } else {
            txtMatKhau.setEchoChar('\u0000');
            btnHide.setText("Ẩn");
        }
    }//GEN-LAST:event_btnHideActionPerformed

    private void rdNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNamActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        index = jtbNguoiHoc.getSelectedRow();

        if (index >= 0) {
            if (validateData()) {
                int jop = JOptionPane.showConfirmDialog(this, "Bạn có muốn update người học ? ", "Update người học", JOptionPane.YES_NO_OPTION);
                NguoiHoc nguoiHocCu = nguoiHocList.get(index);
                NguoiHoc nguoiHocMoi = this.readForm();
                nguoiHocMoi.setMaNH(nguoiHocMoi.getMaNH());
                if (jop == 0) {
                    if (nhsv.update(nguoiHocMoi) != 0) {
                        JOptionPane.showMessageDialog(this, "Update thành công");
                        loadData(1);
                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người học cần sửa trong bảng");
        }


    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        index = jtbNguoiHoc.getSelectedRow();
        if (index >= 0) {
            int jop = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá người học ? ", "Xoá người học", JOptionPane.YES_NO_OPTION);
            if (jop == 0) {
                NguoiHoc nhRemove = nhsv.getAll().get(index);
                if (nhsv.delete(nhRemove) != 0) {
                    JOptionPane.showMessageDialog(this, "Xoá thành công");
                    int currentPage = pagination1.page.getCurrent();
                    loadData(currentPage);
                }
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
            java.util.logging.Logger.getLogger(NguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NguoiHocView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.maven.swing.Button btnHide;
    private com.maven.swing.Button btnSua;
    private com.maven.swing.Button btnThem;
    private com.maven.swing.Button btnXoa;
    private com.maven.swing.Button btnXoa1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelTroi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbNguoiHoc;
    private javax.swing.JLabel lbLogo;
    private com.maven.swing.Pagination pagination1;
    private com.maven.swing.RadioButtonCustom rdNam;
    private com.maven.swing.RadioButtonCustom rdNu;
    private com.maven.swing.TextArea textArea1;
    private com.maven.swing.TextAreaScroll textAreaScroll1;
    private com.maven.swing.TextField txtEmail;
    private com.maven.swing.TextArea txtGhiChu;
    private com.maven.swing.TextField txtMaNguoiHoc;
    private com.maven.swing.TextField txtMaNhanVien;
    private com.maven.swing.PasswordField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgayDangKi;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private com.maven.swing.TextField txtSoDienThoai;
    // End of variables declaration//GEN-END:variables
}
