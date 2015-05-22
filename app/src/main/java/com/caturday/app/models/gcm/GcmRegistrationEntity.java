package com.caturday.app.models.gcm;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GcmRegistrationEntity {
    @SerializedName("registration_id") private String registrationId;
}
