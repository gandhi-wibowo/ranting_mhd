<?php
require APPPATH . '/libraries/REST_Controller.php';
class Notifikasi extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }
  function index_get(){
    if($this->get("One")){
      $id = array(
        "id_user"=>$this->get("One"),
        "status"=>0
      );
      $this->db->where($id);
      $this->db->order_by("id_notifikasi");
      $notif = $this->db->get('tb_notifikasi');
      if($notif->num_rows()>0){
        $this->response($notif->result(), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
  }
  function index_put(){
    if($this->put("Status")){
      $status = array("status"=>1);
      $this->db->where('id_notifikasi', $this->put("Status"));
      $update = $this->db->update('tb_notifikasi', $status);
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
