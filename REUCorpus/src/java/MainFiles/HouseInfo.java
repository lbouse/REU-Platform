package MainFiles;

public class HouseInfo {
    
    private String address;
    private String description;
    private String numberBedBath;
    private String squareFeet;
    private String monthlyPayment;
    private String price;
    private String houseType;
    private String sourceSite;

   public HouseInfo(String siteName)
   {
     this.sourceSite = siteName;
     this.monthlyPayment="N/A";
     this.description = "N/A";
     this.numberBedBath = "N/A";
     this.price = "N/A";
     this.houseType = "N/A";
     this.squareFeet = "N/A";
   }

   public String getAddress() {return address; }
   public String getDescription() {return description;}
   public String getBedBath() {return numberBedBath;}
   public String getSquare() {return squareFeet;}
   public String getMonthlyPayment() {return monthlyPayment ;}
   public String getPrice() {return price;}
   public String getHouseType() {return houseType;}
   public String getSourceSite() {return sourceSite;}
   
   public void setAddress(String address) {this.address = address;}	
   public void setDescription(String description) {this.description = description;}
   public void setBedBath(String numberBedBath) {this.numberBedBath = numberBedBath;}
   public void setSquare(String squareFeet) {this.squareFeet = squareFeet;}
   public void setMonthlyPayment(String monthlyPayment) {this.monthlyPayment = monthlyPayment;}
   public void setPrice(String price) {this.price = price;}
   public void setHouseType(String houseType) {this.houseType = houseType;}

   @Override
   public String toString()
   {
     StringBuffer sb = new StringBuffer();
     sb.append("Address : " + getAddress());
     sb.append("\n");
     sb.append("Description : " + getDescription());
     sb.append("\n");
     sb.append("Number of Beds + Baths : " + getBedBath());
     sb.append("\n");
     sb.append("Square Feet : " + getSquare());
     sb.append("\n");
     sb.append("Price : " + getPrice());
     sb.append("\n");
     sb.append("Monthly Payment: " + getMonthlyPayment());
     sb.append("\n");
     sb.append("Source Site: " + getSourceSite());
     sb.append("\n");
     return sb.toString();
   }
}
