/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.auth.support;

import org.springblade.auth.service.BladeUserDetails;
import org.springblade.auth.utils.TokenUtil;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 * @author Chill
 */
public class BladeJwtTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		BladeUserDetails principal = (BladeUserDetails) authentication.getUserAuthentication().getPrincipal();
		Map<String, Object> info = new HashMap<>(16);
		info.put(TokenUtil.CLIENT_ID, TokenUtil.getClientIdFromHeader());
		info.put(TokenUtil.USER_ID, principal.getUserId());
		info.put(TokenUtil.ROLE_ID, principal.getRoleId());
		info.put(TokenUtil.TENANT_ID, principal.getTenantId());
		info.put(TokenUtil.ACCOUNT, principal.getAccount());
		info.put(TokenUtil.USER_NAME, principal.getUsername());
		info.put(TokenUtil.NICK_NAME, principal.getName());
		info.put(TokenUtil.ROLE_NAME, principal.getRoleName());
		info.put(TokenUtil.AVATAR, principal.getAvatar());
		info.put(TokenUtil.LICENSE, TokenUtil.LICENSE_NAME);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
