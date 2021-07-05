$(document).ready(async function () {

    let response = await fetch("/api/roles");
    let data = await response.json();

    $("#newUserRoles").empty();
    for (let i = 0; i < data.length; i++) {
        $("#newUserRoles").append("<option value='" + data[i].id + "'>" + data[i].role + "</option>");
    }
    getUserDetailInfo($("user_id").val());
    fillUsersTable();
});

async function getUserInfo() {
    $("#userTable tbody").empty();
    let response = await fetch("/api/users/" + $("#user_id").val());
    let data = await response.json();
    $("#userDetailEmail").empty();
    $("#userDetailEmail").append(data.email);

    let tableInfo = "<tr><td>" + data.id + "</td>" + "<td>" + data.name + "</td>" + "<td>"
        + data.surname + "</td>" + "<td>" + data.age + "</td>" + "<td>" + data.email + "</td>"
        + "<td>" + data.username + "</td>" + "<td>";
    $("#userDetailRoles").empty();
    for (let i = 0; i < data.roles.length; i++) {
        tableInfo += data.roles[i].role + " ";
        $("#userDetailRoles").append("<span class='text-white'>" + data.roles[i].role + "</span> ");
    }
    ;
    tableInfo += "</td></tr>";
    $("#userTable tbody").append(tableInfo);
}

async function getUserDetailInfo(user_id) {
    $("#userDetailTable tbody").empty();
    let response = await fetch("/api/users/" + $("#user_id").val());
    let data = await response.json();
    $("#userDetailEmail").empty();
    $("#userDetailEmail").append("<span>" + data.email + "</span>");

    let tableInfo = "<tr><td>" + data.id + "</td>" + "<td>" + data.name + "</td>" + "<td>"
        + data.surname + "</td>" + "<td>" + data.age + "</td>" + "<td>" + data.email + "</td>"
        + "<td>" + data.username + "</td>" + "<td>";
    $("#userDetailRoles").empty();
    for (let i = 0; i < data.roles.length; i++) {
        tableInfo += data.roles[i].role + " ";
        $("#userDetailRoles").append("<span class='text-white'>" + data.roles[i].role + "</span> ");
    };
    tableInfo += "</td></tr>";
    $("#userDetailTable tbody").append(tableInfo);
}

async function editUserModal(id) {
    let response = await fetch("/api/users/" + id);
    let data = await response.json();
    $("#editUserModalForm #editUserId").val(data.id);
    $("#editUserModalForm #editUserName").val(data.name);
    $("#editUserModalForm #editUserSurname").val(data.surname);
    $("#editUserModalForm #editUserAge").val(data.age);
    $("#editUserModalForm #editUserEmail").val(data.email);
    $("#editUserModalForm #editUserPassword").val(data.password);
    $("#editUserModalForm #editUserUsername").val(data.username);
    $("#editUserModalForm #editUserRoles").empty();
    response = await fetch("/api/roles");
    data = await response.json();
    for(let i=0; i<data.length; i++){
        $("#editUserModalForm #editUserRoles").append("<option value='" + data[i].id + "'>" + data[i].role + "</option>");
    }
    $("#editUserModal").modal("show");
}

async function deleteUserModal(id) {
    let response = await fetch("/api/users/" + id);
    let data = await response.json();
    $("#deleteUserModal #deleteUserId").val(data.id);
    $("#deleteUserModal #deleteUserName").val(data.name);
    $("#deleteUserModal #deleteUserSurname").val(data.surname);
    $("#deleteUserModal #deleteUserAge").val(data.age);
    $("#deleteUserModal #deleteUserEmail").val(data.email);
    $("#deleteUserModal #deleteUserId").val(data.id);
    $("#deleteUserModal #deleteUserUsername").val(data.username);
    $("#deleteUserModal #deleteUserRoles").empty();
    for(let i=0; i<data.roles.length; i++){
        $("#deleteUserModal #deleteUserRoles").append("<option>" +data.roles[i].role+"</option>>");
    }
    $("#deleteUserModal").modal("show");
}

async function fillUsersTable() {
    $("#userTable tbody").empty();
    let response = await fetch("/api/users");
    let data = await response.json();
    let tableInfo = "";
    for (let i = 0; i < data.length; i++) {
        tableInfo += "<tr><td>" + data[i].id + "</td>" + "<td>" + data[i].name + "</td>" + "<td>"
            + data[i].surname + "</td>" + "<td>" + data[i].age + "</td>" + "<td>" + data[i].email + "</td>"
            + "<td>" + data[i].username + "</td>" + "<td>";
        for (let j = 0; j < data[i].roles.length; j++) {
            tableInfo += data[i].roles[j].role + " ";
        }
        ;
        tableInfo += "</td>" + `<td><a href="#" class="btn btn-info"
                            onclick="editUserModal('` + data[i].id + `');">Edit</a></td>`;
        tableInfo += "<td>" + `<a href="#" class="btn btn-danger"
                           onclick="deleteUserModal('` + data[i].id + `');">Delete</a></td>`;
        tableInfo += "</tr>";
    }
    $("#userTable tbody").append(tableInfo);
}

$("#userInfoButton").click(function (e) {
    e.preventDefault();
    $("#adminNav").removeClass("navbar-dark");
    $("#adminNav").removeClass("bg-primary");
    $("#adminNav a").removeClass("text-white");
    $("#userNav a").addClass("text-white");
    $("#userNav").addClass("navbar-dark");
    $("#userNav").addClass("bg-primary");
    $("#adminPanelDiv").css({"display": "none"});
    $("#userInfoDiv").css({"display": "block"});
})
;

$("#adminPanelButton").click(function (e) {
    e.preventDefault();
    $("#adminNav").addClass("navbar-dark");
    $("#adminNav").addClass("bg-primary");
    $("#adminNav").removeClass("navbar-light");
    $("#adminNav a").addClass("text-white");
    $("#userNav").removeClass("navbar-dark");
    $("#userNav").removeClass("bg-primary");
    $("#userNav a").removeClass("text-white");
    $("#userNav").addClass("navbar-light");
    $("#adminPanelDiv").css({"display": "block"});
    $("#userInfoDiv").css({"display": "none"});
})
;

$("#addNewUserButton").click(async function (e) {
    e.preventDefault();
    let user = {};
    user.name = $("#newUserForm #newUserName").val();
    user.surname = $("#newUserForm #newUserSurname").val();
    user.age = parseInt($("#newUserForm #newUserAge").val());
    user.email = $("#newUserForm #newUserEmail").val();
    user.username = $("#newUserForm #newUserUsername").val();
    user.password = $("#newUserForm #newUserPassword").val();
    user.roles = [];
    $("#newUserRoles option:selected").each(function (i) {
        let role = {};
        role.id = $(this).val();
        role.role = $(this).text();
        role.authority = $(this).text();
        user.roles[i] = role;
    });
    let json = JSON.stringify(user);
    let response = await fetch("/api/users",{
        method : "POST",
        headers : {
            "Content-type" : "application/json"
        },
        body : json
    });
    let data = await response.json();
    $("#newUserForm")[0].reset();
    getUserDetailInfo($("user_id").val());
    fillUsersTable();
    $("#userTab").click();
});


$("#deleteUserModalButton").click(async function (e) {
    e.preventDefault();
    let id = $("#deleteUserModal #deleteUserId").val();
    let response = await fetch("/api/users/" + id,{
        method : "DELETE",
        headers : {
            "Content-type" : "application/json"
        }
    });
    let result = await response.json();
    fillUsersTable();
    $("#deleteUserModal").modal("hide");
});

$("#editUserModalButton").click(async function (e) {
    e.preventDefault();
    let user = {};
    user.id = $("#editUserModalForm #editUserId").val();
    user.name = $("#editUserModalForm #editUserName").val();
    user.surname = $("#editUserModalForm #editUserSurname").val();
    user.age = parseInt($("#editUserModalForm #editUserAge").val());
    user.email = $("#editUserModalForm #editUserEmail").val();
    user.username = $("#editUserModalForm #editUserUsername").val();
    user.password = $("#editUserModalForm #editUserPassword").val();
    user.roles = [];
    $("#editUserModalForm #editUserRoles option:selected").each(function (i) {
        let role = {};
        role.id = $(this).val();
        role.role = $(this).text();
        role.authority = $(this).text();
        user.roles[i] = role;
    });
    let json = JSON.stringify(user);
    let response = await fetch("/api/users",{
        method : "PUT",
        headers : {
            "Content-type" : "application/json"
        },
        body : json
    });
    let data = await response.json();
    fillUsersTable();
    getUserDetailInfo($("#user_id").val());
    $("#editUserModal").modal("hide");
});

$("#userInformationPanel").on("click",function (e) {
    e.preventDefault();
    getUserInfo();
});