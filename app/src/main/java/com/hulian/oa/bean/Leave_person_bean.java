package com.hulian.oa.bean;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/8/7 14:18
 * Describe:
 */
public class Leave_person_bean {

    /**
     * msg : 操作成功
     * code : 0
     * data : [[{"roleId":100,"roleName":"各部门领导（审批）","deptId":103,"userName":"黄文龙","ancestors":"0,100","userId":121,"parentId":100}],[{"roleId":141,"roleName":"综合管理领导（审批）","deptId":224,"userName":"会议审批人","ancestors":"0,100","userId":358,"parentId":100},{"roleId":141,"roleName":"综合管理领导（审批）","deptId":202,"userName":"曹宇","ancestors":"0,100","userId":185,"parentId":100}],[{"roleId":122,"roleName":"总经理","deptId":203,"userName":"王晓光","ancestors":"0,100","userId":179,"parentId":100}]]
     */

    private String msg;
    private int code;
    private List<List<DataBean>> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * roleId : 100
         * roleName : 各部门领导（审批）
         * deptId : 103
         * userName : 黄文龙
         * ancestors : 0,100
         * userId : 121
         * parentId : 100
         */

        private int roleId;
        private String roleName;
        private int deptId;
        private String userName;
        private String ancestors;
        private int userId;
        private int parentId;

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAncestors() {
            return ancestors;
        }

        public void setAncestors(String ancestors) {
            this.ancestors = ancestors;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }
}
