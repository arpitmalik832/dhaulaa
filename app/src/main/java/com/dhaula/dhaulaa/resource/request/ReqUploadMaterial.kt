package com.projectheen.trunkie.resource.request


data class ReqUploadMaterial(
    var fileName:String?,
    var fileType: String?,
    var url:String?,
    var target:String?,
    var name:String?,
    var loginUserId:Int?,
    var secSubSylMappingId:Int?,
    var resourceType:Int?,
    var dueDate:String?,
    var description:String?,
    var chapterSectionId:Int?,
    var chapterId:Int?,
    var branchId:Int?,
    var sectionId:Int?,
    var subjectId:Int?,
    var attachment:String?
)
