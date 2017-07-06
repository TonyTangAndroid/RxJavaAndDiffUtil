package se.hellsoft.diffutilandrxjava;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

public class WordEntity implements Parcelable {
    public static final Creator<WordEntity> CREATOR = new Creator<WordEntity>() {
        @Override
        public WordEntity createFromParcel(Parcel in) {
            return new WordEntity(in);
        }

        @Override
        public WordEntity[] newArray(int size) {
            return new WordEntity[size];
        }
    };
    private int id;
    private String text;
    @ColorInt
    private int color;

    private WordEntity() {
    }

    private WordEntity(int id, String text, int color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    protected WordEntity(Parcel in) {
        id = in.readInt();
        text = in.readString();
        color = in.readInt();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeInt(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordEntity thing = (WordEntity) o;

        if (id != thing.id) return false;
        if (color != thing.color) return false;
        return text != null ? text.equals(thing.text) : thing.text == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + color;
        return result;
    }

    public static class Builder {
        private int id;
        private String text;
        private int color;

        private Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder color(@ColorInt int color) {
            this.color = color;
            return this;
        }

        public WordEntity build() {
            return new WordEntity(id, text, color);
        }
    }
}
