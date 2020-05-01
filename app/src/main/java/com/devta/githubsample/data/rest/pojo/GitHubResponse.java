package com.devta.githubsample.data.rest.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on : May, 01, 2020 at 14:25
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class GitHubResponse {

    private int total_count;
    private boolean incomplete_results;
    private ArrayList<ItemsBean> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public ArrayList<ItemsBean> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Parcelable {

        private int id;
        private String name;
        @SerializedName("full_name")
        private String fullName;
        private OwnerBean owner;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public static class OwnerBean implements Parcelable {

            private String login;
            private int id;
            private String avatar_url;
            private String type;

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.login);
                dest.writeInt(this.id);
                dest.writeString(this.avatar_url);
                dest.writeString(this.type);
            }

            public OwnerBean() {
            }

            protected OwnerBean(Parcel in) {
                this.login = in.readString();
                this.id = in.readInt();
                this.avatar_url = in.readString();
                this.type = in.readString();
            }

            public static final Parcelable.Creator<OwnerBean> CREATOR = new Parcelable.Creator<OwnerBean>() {
                @Override
                public OwnerBean createFromParcel(Parcel source) {
                    return new OwnerBean(source);
                }

                @Override
                public OwnerBean[] newArray(int size) {
                    return new OwnerBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.fullName);
            dest.writeParcelable(this.owner, flags);
            dest.writeString(this.description);
        }

        public ItemsBean() {
        }

        protected ItemsBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.fullName = in.readString();
            this.owner = in.readParcelable(OwnerBean.class.getClassLoader());
            this.description = in.readString();
        }

        public static final Parcelable.Creator<ItemsBean> CREATOR = new Parcelable.Creator<ItemsBean>() {
            @Override
            public ItemsBean createFromParcel(Parcel source) {
                return new ItemsBean(source);
            }

            @Override
            public ItemsBean[] newArray(int size) {
                return new ItemsBean[size];
            }
        };
    }
}
