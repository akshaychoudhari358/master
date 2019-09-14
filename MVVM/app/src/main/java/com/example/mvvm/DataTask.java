package com.example.mvvm;

public class DataTask {

    private String mFirstName = "Akshay";

    private String mMiddleName = "Vilasrao";

    private String mLastName = "Choudhari";

    private String status;

    private boolean data = false;

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmMiddleName() {
        return mMiddleName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setData(boolean data) {
       this.data = data;
    }

    public boolean isData() {
        return data;
    }

    public interface checkValue{
       void onValueSet();
    }

    checkValue mCheckValue = new checkValue() {
        @Override
        public void onValueSet() {
            if(isData()) {
                setData(false);
            }
            else {
                setData(true);
            }
        }
    };

    public String getStatus() {
        if(data)
        {
            status="Fetched";
            return status;
        }
        status="Update";
        return status;
    }
}
