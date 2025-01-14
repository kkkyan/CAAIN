$(function () {

    let entryContent;

    $.ajax({
        url: '/home/entries',
        type: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        success: (result) => {
            console.log(result);
            content = result['content'];
            entryContent = content;
            $("#tree").treeview({
                data: content,
                emptyIcon: "glyphicon glyphicon-file",
                onNodeSelected: function (event, data) {
                    let nodeId = data.nodeId;
                    // let index = nodeId.split('.');
                    // let awardPath = entryContent[index[1]]['text'];
                    // let entryPath = entryContent[index[1]].nodes[index[2]].text;
                    // let attachPath = entryContent[index[1]].nodes[index[2]].nodes[index[3]].text;
                    // let filePath = awardPath + '/' + entryPath + '/' + attachPath + '.pdf';
                    // console.log(filePath);
                    // console.log(data.nodeId);
                    if (data.clickable) {
                        console.log(data.file_path);
                        let split = data.file_path.split(".");
                        let suffix = split[split.length-1];

                        if (suffix == "pdf" || suffix == "PDF"){
                            $('#pdf_viewer').attr("data", data.file_path);
                            $('#pdf_viewer').show();
                            $('#img_viewer').hide();
                        }else{
                            $('#img_viewer').attr("src", data.file_path);
                            $('#pdf_viewer').hide();
                            $('#img_viewer').show();
                        }
                    }
                }
            })
        }
    });
});