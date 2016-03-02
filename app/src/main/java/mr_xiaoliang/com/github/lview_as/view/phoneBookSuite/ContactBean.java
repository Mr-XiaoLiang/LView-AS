package mr_xiaoliang.com.github.lview_as.view.phoneBookSuite;
/**
 * 联系人的Bean
 * @author LiuJ
 * 电话本列表套件之一
 */
public class ContactBean {
	private int contactId; //id  
    private String desplayName;//姓名  
    private String phoneNum; // 电话号码  
    private String sortKey; // 排序用的  
    private Long photoId; // 图片id  
    private String lookUpKey;   
    private int selected = 0;  
    private String formattedNumber;  
    private String pinyin; // 姓名拼音
    private boolean isChecked = false;//是否勾选
    private boolean isPhone = true;//是否是电话号码
    private String letter;//首字母
	public ContactBean() {
		super();
	}
	

	public ContactBean(int contactId, String desplayName, String phoneNum, String sortKey, Long photoId,
			String lookUpKey, int selected, String formattedNumber, String pinyin, boolean isChecked, boolean isPhone,
			String letter) {
		this();
		this.contactId = contactId;
		this.desplayName = desplayName;
		this.phoneNum = phoneNum;
		this.sortKey = sortKey;
		this.photoId = photoId;
		this.lookUpKey = lookUpKey;
		this.selected = selected;
		this.formattedNumber = formattedNumber;
		this.pinyin = pinyin;
		this.isChecked = isChecked;
		this.isPhone = isPhone;
		this.letter = letter;
	}


	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getDesplayName() {
		return desplayName;
	}
	public void setDesplayName(String desplayName) {
		this.desplayName = desplayName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public Long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	public String getLookUpKey() {
		return lookUpKey;
	}
	public void setLookUpKey(String lookUpKey) {
		this.lookUpKey = lookUpKey;
	}
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	public String getFormattedNumber() {
		return formattedNumber;
	}
	public void setFormattedNumber(String formattedNumber) {
		this.formattedNumber = formattedNumber;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isPhone() {
		return isPhone;
	}

	public void setPhone(boolean isPhone) {
		this.isPhone = isPhone;
	}


	public String getLetter() {
		return letter;
	}


	public void setLetter(String letter) {
		this.letter = letter;
	}
	
}
