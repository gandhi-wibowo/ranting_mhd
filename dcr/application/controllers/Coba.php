<?php
require APPPATH . '/libraries/REST_Controller.php';
class Coba extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }

  function index_get(){
    $waktu = DateTime();
    $this->db->select("id_user");
    $this->db->where("id_event",$this->get("id_event"));
    $User = $this->db->get("tb_event")->row_array();
    $this->db->select("token");
    $this->db->where("id_user",$User['id_user']);
    $Poster = $this->db->get("tb_users")->row_array();
    $this->db->select("nama_user");
    $this->db->where("id_user",$this->get("id_user"));
    $NamaUser = $this->db->get("tb_users")->row_array();
    $notifikasi = $NamaUser['nama_user']." Mengomentari Event anda";
    $data = array(
      "id_event"=>$this->get("id_event"),
      "id_user"=>$this->get("id_user"),
      "isi_notifikasi"=>$notifikasi,
      "tgl_notifikasi"=>$waktu,
      "status"=>0
    );
    $this->db->insert("tb_notifikasi",$data);
    FCM($Poster['token'],"Komentar baru",$notifikasi);
    $this->db->select("id_user");
    $this->db->where("id_event",$this->get("id_event"));
    $id = $this->db->get("tb_komentar")->result();
    foreach ($id as $key => $value) {
      $this->db->select("token");
      $this->db->where("id_user",$value->id_user);
      $tok = $this->db->get("tb_users")->row_array();
      // disini dapat 3 id
      $this->db->select("nama_user");
      $this->db->where("id_user",$value->id_user);
      $name = $this->db->get("tb_users")->row_array();
      $not = ucfirst($name['nama_user'])." Mengomentari event yang anda ikuti";
      $noti = array(
        "id_event"=>$this->get("id_event"),
        "id_user"=>$value->id_user,
        "isi_notifikasi"=>$not,
        "tgl_notifikasi"=>$waktu,
        "status"=>0
      );
      $this->db->insert("tb_notifikasi",$noti);
      FCM($tok['token'],"Komentar baru",$not);
    }

  }
  function index_post(){


    $this->db->select("token");
    $this->db->where("id_user",$this->post("id_user"));
    $Poster = $this->db->get("tb_users")->row_array();
    print_r($Poster['token']);
  }
}
