package com.example.habin.shopcar.salaryTest.Entity;

import java.io.Serializable;
import java.util.List;

public class recordDetailsEntity  {


    /**
     *    success : true
     *     message : "操作成功！"
     *     code : 200
     *     data
     *     timestamp : 1577263350642
     */



    private boolean success;
    private String message;
    private int code;
    private List<RecordData> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RecordData> getData() {
        return data;
    }

    public void setData(List<RecordData> data) {
        this.data = data;
    }

    public static class RecordData implements Serializable{
        /**
         "id": 1,
         "fieldTypeName": "基本信息",
         "fieldTypeSequence": 5,
         "salaryRecordInfoEditVOList": [
         {
         "id": 860,
         "fieldName": "教师编号",
         "fieldValue": "2019007",
         "fieldTypeId": 1,
         "sequence": 29,
         "recordId": null
         }]
         *
         */
        private int id;
        private String fieldTypeName;
        private String fieldTypeSequence;
        private List<salaryRecordInfoEditVO> salaryRecordInfoEditVOList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFieldTypeName() {
            return fieldTypeName;
        }

        public void setFieldTypeName(String fieldTypeName) {
            this.fieldTypeName = fieldTypeName;
        }

        public String getFieldTypeSequence() {
            return fieldTypeSequence;
        }

        public void setFieldTypeSequence(String fieldTypeSequence) {
            this.fieldTypeSequence = fieldTypeSequence;
        }

        public List<salaryRecordInfoEditVO> getSalaryRecordInfoEditVOList() {
            return salaryRecordInfoEditVOList;
        }

        public void setSalaryRecordInfoEditVOList(List<salaryRecordInfoEditVO> salaryRecordInfoEditVOList) {
            this.salaryRecordInfoEditVOList = salaryRecordInfoEditVOList;
        }

        public static class salaryRecordInfoEditVO{

            private int id;
            private String fieldName;
            private String fieldValue;
            private int fieldTypeId;
            private int sequence;
            private String recordId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getFieldValue() {
                return fieldValue;
            }

            public void setFieldValue(String fieldValue) {
                this.fieldValue = fieldValue;
            }

            public int getFieldTypeId() {
                return fieldTypeId;
            }

            public void setFieldTypeId(int fieldTypeId) {
                this.fieldTypeId = fieldTypeId;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public String getRecordId() {
                return recordId;
            }

            public void setRecordId(String recordId) {
                this.recordId = recordId;
            }
        }
    }
}
