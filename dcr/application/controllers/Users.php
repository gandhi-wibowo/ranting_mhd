<?php
require APPPATH . '/libraries/REST_Controller.php';
class Users extends REST_Controller {

  function __construct($config = 'rest') {
      parent::__construct($config);
  }

  function index_get(){
    $uname = $this->get("username");
    $pwd = $this->get("password");
    if($this->get('Login')){
      $users = array(
        "user_login"=>$uname,
        "password"=>sha1($pwd)
      );
      $this->db->where($users);
      $user = $this->db->get('tb_users');
      if($user->num_rows()>0){
        $data = array();
        foreach ($user->result() as $key => $value) {
          $data['status']="sukses";
          $data['id_user']=$value->id_user;
          $data['nama_user']=$value->nama_user;
          $data['hp_user']=$value->hp_user;
          $data['password']=$value->password;
          $data['no_induk']=$value->no_induk;
          $data['user_login']=$value->user_login;
          $data['level']=$value->level;
        }
        $this->response(array($data), 200);
      }
      else{
        $data = array("status"=>"gagal");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("CekLogin")){
      $users = array(
        "user_login"=>$uname,
        "password"=>$pwd
      );
      $this->db->where($users);
      $user = $this->db->get('tb_users');
      if($user->num_rows()>0){
        $data = array("status"=>"sukses");
        $this->response(array($data), 200);
      }
      else{
        $data = array("status"=>"gagal");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("ById")){
      $users = array(
        "id_user"=>$this->get("ById")
      );
      $this->db->where($users);
      $user = $this->db->get('tb_users');
      if($user->num_rows()>0){
        $this->response($user->result(), 200);
      }
      else{
        $data = array("status"=>"gagal");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("ByParent")) {
      $users = array(
        "parent_id"=>$this->get("ByParent")
      );
      $this->db->select("tb_users.*,tb_dcr.*");
      $this->db->join("tb_dcr","tb_dcr.id_user = tb_users.id_user");
      $this->db->where($users);
      $user = $this->db->get('tb_users');
      if($user->num_rows()>0){
        $this->response($user->result(), 200);
      }
      else{
        $data = array("status"=>"gagal");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("ByLevel")) {
      $users = array(
        "level"=>$this->get("ByLevel")
      );
      $this->db->select("tb_users.*,tb_dcr.*");
      $this->db->join("tb_dcr","tb_dcr.id_user = tb_users.id_user");
      $this->db->where($users);
      $user = $this->db->get('tb_users');
      if($user->num_rows()>0){
        $this->response($user->result(), 200);
      }
      else{
        $data = array("status"=>"gagal");
        $this->response(array($data), 200);
      }
    }
    else{
      $data = array("status"=>"gagal");
      $this->response(array($data), 200);
    }
  }

  function index_post(){// tambah users
    if($this->post("Level")==1){
      // kalau level yg ngeadd  = 1
      // input user dengan level PDM
      $level = 2;
    }
    else if($this->post("Level")==2){
      // kalau level yg ngeadd  = 2
      // input user dengan level PCM
      $level = 3;
    }
    else if($this->post("Level")==3){
      // kalau level yg ngeadd  = 3
      // input user dengan level PRM
      $level = 4;
    }
    $users = array(
      "no_induk"=>$this->post("no_induk"),
      "parent_id"=>$this->post("id_user"),
      "user_login"=>$this->post("user_login"),
      "password"=>sha1($this->post("password")),
      "level"=>$level,
      "nama_user"=>$this->post("nama_user"),
      "hp_user"=>$this->post("hp_user")
    );
    $insert = $this->db->insert("tb_users",$users);
    if ($insert) {
      $respon = array("status"=>"sukses");
      $this->response(array($respon), 200);
    } else {
      $respon = array("status"=>"gagal");
      $this->response(array($respon), 200);
    }
  }
  function index_put(){// update data users
    $idUser = $this->put("idUser");
    if($this->put("Users")){
      $user = array(
        "nama_user"=>$this->put("nama_user"),
        "no_induk"=>$this->put("no_induk"),
        "hp_user"=>$this->put("hp_user")
      );
      $this->db->where('id_user', $idUser);
      $update = $this->db->update('tb_users', $user);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else if($this->put("Password")){
      $cekPwd = array(
        "id_user"=>$idUser,
        "password"=>sha1($this->put("OldPassword"))
      );
      $this->db->where($cekPwd);
      $cek = $this->db->get("tb_users");
      if($cek->num_rows()>0){
        // kalau password lama cocok
        $password = array("password"=>sha1($this->put("NewPassword")));
        $this->db->where('id_user', $idUser);
        $update = $this->db->update('tb_users', $password);
        if ($update) {
          $respon = array("status"=>"sukses");
          $this->response(array($respon), 200);
        } else {
          $respon = array("status"=>"gagal");
          $this->response(array($respon), 200);
        }
      }
      else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else if($this->put("Token")){
      $token = array("token"=>$this->put("token"));
      $this->db->where('id_user', $idUser);
      $update = $this->db->update('tb_users', $token);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
  }
}
