
$(document).ready(function () {
    $("#submit").on("click", function () {
        var name = $("#name").val();
        // var files = document.getElementById('image').files;
        var form = $("#form").serialize();
        var files = new FormData($("#form")[0]);
        // $.each(files, function (index) {
        //     data.append(files[index].name, files[index]);
        // });
              for (var [a, b] of files.entries()) {
                   console.log(a, b);
               }
        if (files === null) {
            $("#submit").prop("disabled", false);
            $("#name").css("border-color", "red");
            $("#image").css("border-color", "red");
            $("#error_file").html("Please fill the required field.");
            // $("#error_description").html("Please fill the required field.");
        } else {
            $("#image").css("border-color", "");
            $('#error_name').css('opacity', 0);
            $('#error_file').css('opacity', 0);
            $.ajax({
                type:'POST',
                enctype: 'multipart/for-data',
                data: files,
                url: "/upload",
                processData: false,
                contentType: false,
                cache: false,
                success: function (data, statusText, xhr) {
                  console.log(xhr.status);
                  if (xhr.status === "200") {
                    $('#loader').hide();
                    $('#form')[0].reset();
                    $('#success').css('display', 'block');
                    $('#error').text("");
                    $('#success').html("Image Added Successfully");
                    $('#success').delay(2000).fadeOut('slow');
                    location.reload();
                  }
                },
                error: function (e) {
                    $('#error').css('display','block');
                    $("#error").html("Oops! something went wrong.");
                    $('#error').delay(5000).fadeOut('slow');
                    location.reload();
                }
            });
        }
    });
});