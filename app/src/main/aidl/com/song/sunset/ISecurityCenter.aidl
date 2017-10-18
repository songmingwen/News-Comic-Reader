package com.song.sunset;

interface ISecurityCenter{
	String encrpt(String content);
	String decrpt(String password);
}