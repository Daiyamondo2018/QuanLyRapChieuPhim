package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Connector.Connector;
import Model.KhachHang;
import Model.NhanVien;
import Model.Phim;
import Model.PhongChieuPhim;
import Model.TaiKhoan;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import plugin.AlertBox;
import plugin.MyWindows;
import plugin.SceneController;
import usercontrol.control.AddEditAccount;
import usercontrol.control.AddEditInfo;
import usercontrol.control.AddEditRoomInfo;

public class AccountController implements Initializable {

	@FXML
	private Label lblStart;
	@FXML
	private Label lblDoB;
	@FXML
	private Label lblGender;
	@FXML
	private Label lblAdd;

	@FXML
	private Button changeAvatar;
	@FXML
	private BorderPane pane;
	@FXML
	private GridPane gp;
	@FXML
	private VBox leftParentVBox;
	@FXML
	private HBox topLeftHBox;
	@FXML
	private Label userName;
	@FXML
	private Label topName;
	@FXML
	private Label topEmail;
	@FXML
	private Label passWord;
	@FXML
	private Label nickName;
	@FXML
	private Label Name;
	@FXML
	private Label gender;
	@FXML
	private Label dob;
	@FXML
	private Hyperlink email;
	@FXML
	private Label phoneNumber;
	@FXML
	private Label address;
	@FXML
	private Label startDay;
	@FXML
	private ImageView avatar;

	NhanVien mNhanVien = null;
	KhachHang mKhachHang = null;
	TaiKhoan mTaiKhoan = null;

	private AddEditAccount EditInfo;
	private AddEditInfo EditNickname = new AddEditInfo("Thay đổi tên đăng nhập");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initial();
	}

	private void initial() {
		// TODO Auto-generated method stub
		if (LoginController.taikhoan == null)
			return;
		mTaiKhoan = LoginController.taikhoan;
		refresh();
	}

	private void refresh() {
		// TODO Auto-generated method stub
		setUpTaiKhoan();

		if (checkNV(mTaiKhoan.getMaTaiKhoan())) {
			setUpNhanVien();
		} else
			setUpCustomer();
	}

	private void setUpTaiKhoan() {
		// TODO Auto-generated method stub
		Image img = convertToBufferImage(mTaiKhoan.getHinhAnh());
		Rectangle2D croppedPortion;
		if (img.getWidth() / 130 > img.getHeight() / 130) {

			croppedPortion = new Rectangle2D(img.getWidth() / 2.0 - img.getHeight() / 130 * 65, 0,
					img.getHeight() / 130 * 130, img.getHeight());

		} else {
			croppedPortion = new Rectangle2D(0, img.getHeight() / 2.0 - img.getWidth() / 130 * 65, img.getWidth(),
					img.getWidth() / 65.0 * 65.0);
		}

		// target width and height:
		double scaledWidth = 130;
		double scaledHeight = 130;
		avatar.setImage(img);
		avatar.setViewport(croppedPortion);
		avatar.setFitWidth(scaledWidth);
		avatar.setFitHeight(scaledHeight);
		avatar.setSmooth(false);
		avatar.setImage(img);
		topName.setText(mTaiKhoan.getTenHienThi());
		userName.setText(mTaiKhoan.getTenDangNhap());
		String pw = "";
		for (int i = 0; i < mTaiKhoan.getMatKhau().length(); i++) {
			pw += "*";
		}
		passWord.setText(pw);
		nickName.setText(mTaiKhoan.getTenHienThi());
	}

	private void setUpCustomer() {
		// TODO Auto-generated method stub

		Connector<KhachHang> connector = new Connector<KhachHang>();

		List<KhachHang> ds=connector.select(KhachHang.class,
				"select * from KhachHang where MATAIKHOAN = '" + mTaiKhoan.getMaTaiKhoan() + "'");
		if(ds.size()==0) {
			return;
		}
		mKhachHang = ds.get(0);

		Name.setText(mKhachHang.getHoTen());
//		gender.setText(mNhanVien.getGioiTinh());
//		dob.setText(mNhanVien.getNgaySinh());
		topEmail.setText(mKhachHang.getEmail());
		email.setText(mKhachHang.getEmail());
		phoneNumber.setText(mKhachHang.getSoDienThoai());
//		address.setText(mNhanVien.getDiaChi());
//		startDay.setText(mNhanVien.getNgayVaoLam());

		gender.setVisible(false);
		address.setVisible(false);
		startDay.setVisible(false);
		dob.setVisible(false);
		lblAdd.setVisible(false);
		lblDoB.setVisible(false);
		lblGender.setVisible(false);
		lblStart.setVisible(false);
	}

	private void setUpNhanVien() {
		// TODO Auto-generated method stub
		
		Connector<NhanVien> connector = new Connector<NhanVien>();
		mNhanVien = connector
				.select(NhanVien.class, "select * from NHANVIEN where MATAIKHOAN = '" + mTaiKhoan.getMaTaiKhoan() + "'")
				.get(0);
		try {
			connector.connect().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Name.setText(mNhanVien.getHoTen());
		gender.setText(mNhanVien.getGioiTinh());
		dob.setText(mNhanVien.getNgaySinh());
		topEmail.setText(mNhanVien.getEmail());
		email.setText(mNhanVien.getEmail());
		phoneNumber.setText(mNhanVien.getSoDienThoai());
		address.setText(mNhanVien.getDiaChi());
		startDay.setText(mNhanVien.getNgayVaoLam());
	}

	private boolean checkNV(String maTaiKhoan) {
		// TODO Auto-generated method stub
		Connector<TaiKhoan> connector = new Connector<TaiKhoan>();
		int count = 0;
		try {
			Connection connection = connector.connect();
			Statement statement;
			ResultSet result = null;
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT MaTaiKhoan FROM NhanVien Where MaTaiKhoan = '" + maTaiKhoan + "'");
			if (result.next()) {
				count++;
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connector.connect().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(count);

		if (count == 0)
			return false;
		return true;
	}

	@FXML
	void UpdateInfo(ActionEvent event) {
		//EditInfo = 
		if (checkNV(mTaiKhoan.getMaTaiKhoan())) {
			editNhanVien();
		} else {
			editCustomer();
		}

	}

	private void editNhanVien() {
		// TODO Auto-generated method stub
		EditInfo = new AddEditAccount("Cập nhật thông tin tài khoản");
		String[] tmp = { "Họ và tên", "Tên hiển thị", "Ngày sinh", "Email", "Số điện thoại", "Địa chỉ" };
		EditInfo.AddAll(tmp);
		EditInfo.addImageView("Cập nhật avatar:", avatar.getImage());

		Connector<NhanVien> connector = new Connector<NhanVien>();
		
		mNhanVien = connector
				.select(NhanVien.class, "select * from NHANVIEN where MATAIKHOAN = '" + mTaiKhoan.getMaTaiKhoan() + "'")
				.get(0);
		try {
			connector.connect().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EditInfo.Get("Họ và tên").setText(mNhanVien.getHoTen());
		EditInfo.Get("Tên hiển thị").setText(mTaiKhoan.getTenHienThi());
		EditInfo.Get("Ngày sinh").setText(mNhanVien.getNgaySinh());
		EditInfo.Get("Email").setText(mNhanVien.getEmail());
		EditInfo.Get("Số điện thoại").setText(mNhanVien.getSoDienThoai());
		EditInfo.Get("Địa chỉ").setText(mNhanVien.getDiaChi());
		EditInfo.show();

		if (EditInfo.boxReturn == ButtonType.CANCEL)
			return;
		if (EditInfo.boxReturn == ButtonType.OK) {
			try {
				String hoten = EditInfo.Get("Họ và tên").getText();
				String nickname = EditInfo.Get("Tên hiển thị").getText();
				String dob = EditInfo.Get("Ngày sinh").getText();
				String email = EditInfo.Get("Email").getText();
				String phone = EditInfo.Get("Số điện thoại").getText();
				String address = EditInfo.Get("Địa chỉ").getText();
				String matk = mTaiKhoan.getMaTaiKhoan();
				
				if (EditInfo.f != null) {
					new Connector<NhanVien>().update("update NHANVIEN set HoTen ='" + hoten + "', NgaySinh='" + dob
							+ "',DiaChi='" + address + "', Email='" + email + "', SoDienThoai='" + phone
							+ "' where mataikhoan = '" +matk + "'");

					new Connector<TaiKhoan>().update("update TAIKHOAN set TenHienThi ='" + nickname + "', "
							+ "', HinhAnh=? where MaTaiKhoan='" + mTaiKhoan.getMaTaiKhoan() + "'",
							Connector.convertFileToByte(EditInfo.f));
				} else {
					new Connector<NhanVien>().update("update NHANVIEN set HoTen ='" + hoten + "', NgaySinh='" + dob
							+ "',DiaChi='" + address + "', Email='" + email + "', SoDienThoai='" + phone
							+ "' where mataikhoan = '" + matk + "'");
					new Connector<TaiKhoan>()
							.update("update TAIKHOAN set TenHienThi ='" + nickname + "' where MaTaiKhoan=' " + mTaiKhoan.getMaTaiKhoan()+"'");
				}
				
				mTaiKhoan = new Connector<TaiKhoan>()
						.selectTaiKhoan("Select * from TaiKhoan where MaTaiKhoan = '"+mTaiKhoan.getMaTaiKhoan()+"'").get(0);
				
				refresh();

			} catch (Exception e) {
				// TODO: handle exception
				AlertBox.show(AlertType.WARNING, "Nhập sai", "", "Vui lòng kiểm tra lại thông tin");
			}
		}
		
	}

	private void editCustomer() {
		// TODO Auto-generated method stub

	}

	@FXML
	void Logout(ActionEvent event) {
		application.MainController.SelectedButton.setValue("Home");
		plugin.SceneController.GetInstance().TryReplaceScene("Login");
	}

	@FXML
	void ChangePass(ActionEvent event) {
		MyWindows w = new MyWindows("../view/ChangePassword.fxml");
		w.Show();

	}

	@FXML
	void ChangeNickname(ActionEvent event) {

		EditNickname.show();
	}

	@FXML
	void ChangeAvatar(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.showOpenDialog(SceneController.GetInstance().getCurrentStage());
	}

	@FXML
	void CopyEmail(ActionEvent event) {
		AlertBox.show(AlertType.INFORMATION, "Thông tin", "Đã chép vào bộ nhớ tạm");
	}

	public static Image convertToBufferImage(byte[] data) {
		Image img = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			BufferedImage bImage2 = ImageIO.read(bis);
//			ImageIO.write(bImage2, "jpg", new File("output.jpg") );
			img = SwingFXUtils.toFXImage(bImage2, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return img;
	}
}
