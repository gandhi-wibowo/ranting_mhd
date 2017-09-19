<?php
require APPPATH . '/libraries/REST_Controller.php';
class Komentar extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }
  function index_get(){
    if($this->get("One")){// ambil comentar berdasarkan id event
      $data = array("id_event"=>$this->get("id_event"));
      $this->db->select("tb_komentar.*,tb_users.nama_user");
      $this->db->where($data);
      $this->db->join("tb_users","tb_users.id_user = tb_komentar.id_user");
      $this->db->order_by("tb_komentar.tgl_komentar","ASC");
      $komentar = $this->db->get('tb_komentar');
      if($komentar->num_rows()>0){
        $this->response($komentar->result(), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else{
      $data = array("status"=>"data_kosong");
      $this->response(array($data), 200);
    }
  }
  function index_post(){
    $waktu = DateTime();
    if($this->post("simpan")){
      $data = array(
        "id_event"=>$this->post("id_event"),
        "id_user"=>$this->post("id_user"),
        "komentar"=>$this->post("komentar"),
        "tgl_komentar"=>$waktu
      );
      $simpan = $this->db->insert("tb_komentar",$data);
      if($simpan){
        $this->db->select("id_user");
        $this->db->where("id_event",$this->post("id_event"));
        $User = $this->db->get("tb_event")->row_array();// id user yg punya event
        $this->db->select("token");
        $this->db->where("id_user",$User['id_user']); // ambil tokennya
        $Poster = $this->db->get("tb_users")->row_array();
        $this->db->select("nama_user");
        $this->db->where("id_user",$this->post("id_user")); // ambil nama komentator  berdasarkan id_user
        $NamaUser = $this->db->get("tb_users")->row_array();
        $notifikasi = ucfirst($NamaUser['nama_user'])." Mengomentari Event anda";
        $data = array(
          "id_event"=>$this->post("id_event"),
          "id_user"=>$User['id_user'],
          "isi_notifikasi"=>$notifikasi,
          "tgl_notifikasi"=>$waktu,
          "status"=>0
        );
        if($User["id_user"] != $this->post("id_user")){
          // kalo yg komentar yg bikin event, dia gk dapet notif
          $this->db->insert("tb_notifikasi",$data);
          FCM($Poster['token'],"Komentar baru",$notifikasi);
        }


        $this->db->select("id_user");
        $this->db->where("id_event",$this->post("id_event"));
        $id = $this->db->get("tb_komentar")->result();// pilih semua user yang ikut komentar
        foreach ($id as $key => $value) {
          $this->db->select("token");
          $this->db->where("id_user",$value->id_user);
          $tok = $this->db->get("tb_users")->row_array(); // ambil token berdasarkan id yang di dapat
          // disini dapat 3 id
          $not = ucfirst($NamaUser['nama_user'])." Mengomentari event yang anda ikuti";
          $noti = array(
            "id_event"=>$this->post("id_event"),
            "id_user"=>$value->id_user,
            "isi_notifikasi"=>$not,
            "tgl_notifikasi"=>$waktu,
            "status"=>0
          );
          if($value->id_user != $this->post("id_user")){ // kalo id yang ngirim gk sama, kirim notif
            $this->db->insert("tb_notifikasi",$noti);
            FCM($tok['token'],"Komentar baru",$not);
          }


        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      }
      }
      else{
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
  }
  function index_put(){

  }
}
