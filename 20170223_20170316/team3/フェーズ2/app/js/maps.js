// �L�����p�X�̗v�f���擾����
var canvas = document.getElementById( 'map-canvas' ) ;

// ���S�̈ʒu���W���w�肷��
var latlng = new google.maps.LatLng( 35.792621, 139.806513 );

// �n�}�̃I�v�V������ݒ肷��
var mapOptions = {
	zoom: 15 ,	// �Y�[���l
	center: latlng ,	// ���S���W [latlng]
};

// [canvas]�ɁA[mapOptions]�̓��e�́A�n�}�̃C���X�^���X([map])���쐬����
var map = new google.maps.Map( canvas, mapOptions ) ;