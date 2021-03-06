const title = document.getElementById('post-title');
const content = document.getElementById('post-inner');
const content_img = document.getElementById('post-img');
const userid = document.getElementById('userID');
const span = document.createElement('span');
const delFiles = {
    deletedFiles  : []
};

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
const urlParams = getParameterByName('boardid');

fetch(`/api/board/post/${urlParams}`)
    .then((res)=>res.json())
    .then((data)=>
    {
        for(let i =0; i< data.files.length; i++){
            const img = document.createElement('img');
            const divImg = document.createElement('div');
            const deleteButton = document.createElement('button');
            divImg.classList.add(`current-img`);
            divImg.id= `${data.files[i].file_name}`
            deleteButton.classList.add('img-btn-delete','btn-danger');
            deleteButton.innerText = 'X';
            img.classList.add(`img-item`);
            img.src= data.files[i].file_path;

            deleteButton.addEventListener('click',(e)=>{
                console.log(e);
                delFiles.deletedFiles.push({"value":e.target.offsetParent.id});
                e.target.parentElement.remove();
            })

            divImg.appendChild(img);
            divImg.appendChild(deleteButton);
            content_img.appendChild(divImg);
        }


        editor.setData(data.post.bd_content);
        title.value = data.post.bd_title;
        span.innerText= data.post.bd_writer;
        userid.appendChild(span);

    });


// {
//     deletefiles : [
//         {
//             "1": "value",
//         }
//     ]
// }