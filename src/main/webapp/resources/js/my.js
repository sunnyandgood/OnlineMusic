/**
 *
 * @param el         上传按钮el
 * @param multi      是否支持多文件上传 value=true|false
 * @param url        上传地址
 * @param webpath    web根路径
 */
function upload(el,multi,url,webpath){
    el.each(function(){
        var aa = function(obj){
            obj.Huploadify({
                auto:true,
                fileTypeExts:'*.mp3;*.png;*.xls;*.xlsx;',
                multi:multi,
                //formData:{name:'image0'},
                fileSizeLimit:9999,
                showUploadedPercent:true,//是否实时显示上传的百分比，如20%
                showUploadedSize:true,
                removeTimeout:9999999,
                uploader:url,
                onInit:function(file){
                },
                onUploadComplete:function(file,path,response){
                    //获得回填数据
                    src = webpath+path;
                    //此处obj为上传控件  ,代表每个imageUpload
                    //回填表单
                    obj.before('<input type="hidden" name="'+obj.attr('name')+'" value="'+path+'"/>');
                    //填充预览图
                    // obj.after('<img src="'+src+'" style="height: 100px;"/>');
                    setTimeout(function(){
                        obj.find('.uploadify-queue-item').html('');
                    },1000);
                }
            });
        }
        aa($(this));
    });
}
