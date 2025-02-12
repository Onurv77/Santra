package com.example.santra.data.entities

data class PostWithProfile(val postId: Int,
                           val postStudentId: String,
                           val postGroupName: String,
                           val postParticipantNum: Int,
                           val postDescription: String?,
                           val postDate: Long?,
                           val postMevki: String?,
                           val profileUsername: String?,
                           val profileAvatarUrl: ByteArray?,
                           val profileRank: String?,
                           val profilePhone: String?)
