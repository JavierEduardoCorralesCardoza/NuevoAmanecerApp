/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.credentials;

import static java.util.Objects.requireNonNull;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

/**
 * A request class for clearing a user's credential state from the credential providers.
 */
public final class ClearCredentialStateRequest implements Parcelable {

    /** The request data. */
    @NonNull
    private final Bundle mData;

    /** Returns the request data. */
    @NonNull
    public Bundle getData() {
        return mData;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeBundle(mData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ClearCredentialStateRequest {data=" + mData + "}";
    }

    /**
     * Constructs a {@link ClearCredentialStateRequest}.
     *
     * @param data the request data
     */
    public ClearCredentialStateRequest(@NonNull Bundle data) {
        mData = requireNonNull(data, "data must not be null");
    }

    private ClearCredentialStateRequest(@NonNull Parcel in) {
        Bundle data = in.readBundle();
        mData = data;
        AnnotationValidations.validate(NonNull.class, null, mData);
    }

    public static final @NonNull Creator<ClearCredentialStateRequest> CREATOR =
            new Creator<ClearCredentialStateRequest>() {
        @Override
        public ClearCredentialStateRequest[] newArray(int size) {
            return new ClearCredentialStateRequest[size];
        }

        @Override
        public ClearCredentialStateRequest createFromParcel(@NonNull Parcel in) {
            return new ClearCredentialStateRequest(in);
        }
    };
}
