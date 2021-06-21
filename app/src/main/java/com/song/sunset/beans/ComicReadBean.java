package com.song.sunset.beans;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by Song on 2016/9/21 0021.
 * Email:z53520@qq.com
 */
@Keep
public class ComicReadBean {

    private String chapter_id;

    private String type;

    private String zip_file_high;

    private List<ImageListBean> image_list;

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZip_file_high() {
        return zip_file_high;
    }

    public void setZip_file_high(String zip_file_high) {
        this.zip_file_high = zip_file_high;
    }

    public List<ImageListBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImageListBean> image_list) {
        this.image_list = image_list;
    }

    public static class ImageListBean {
        private String location;
        private String image_id;
        private int width;
        private int height;
        private String total_tucao;
        private String webp;
        private String type;
        private String size;
        private String img05;
        private String img50;

        private List<ImagesBean> images;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getImage_id() {
            return image_id;
        }

        public void setImage_id(String image_id) {
            this.image_id = image_id;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getTotal_tucao() {
            return total_tucao;
        }

        public void setTotal_tucao(String total_tucao) {
            this.total_tucao = total_tucao;
        }

        public String getWebp() {
            return webp;
        }

        public void setWebp(String webp) {
            this.webp = webp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getImg05() {
            return img05;
        }

        public void setImg05(String img05) {
            this.img05 = img05;
        }

        public String getImg50() {
            return img50;
        }

        public void setImg50(String img50) {
            this.img50 = img50;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            private String id;
            private String sort;
            private int width;
            private int height;
            private String img05;
            private String img50;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getImg05() {
                return img05;
            }

            public void setImg05(String img05) {
                this.img05 = img05;
            }

            public String getImg50() {
                return img50;
            }

            public void setImg50(String img50) {
                this.img50 = img50;
            }
        }
    }
}
