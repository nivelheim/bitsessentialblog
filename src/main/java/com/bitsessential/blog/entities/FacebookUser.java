package com.bitsessential.blog.entities;

public class FacebookUser {
		String providerUserId;
		String displayName;
		
		public FacebookUser(String user, String display) {
			this.providerUserId = user;
			this.displayName = display;
		}

		public String getProviderUserId() {
			return providerUserId;
		}
		public void setProviderUserId(String providerUserId) {
			this.providerUserId = providerUserId;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		
}
