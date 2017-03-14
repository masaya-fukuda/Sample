  $('#method').click(function() {

		// �O���[�o���ϐ�
		var map;
		//console.log('map:' + map);
		//console.log(document.idokeido.ido.value);
		var ido = document.forms.idokeido.ido.value;
		var keido = document.forms.idokeido.keido.value;

		console.log('ido:' + ido);
		console.log('keido:' + keido);

		// LatLngLiteral���쐬
		var LatLngLiteral = {
			lat: ido,
			lng: keido,
		//	lat: 35.421 ,	// �ܓx
		//	lng: 139.4621 ,	// �o�x
		} ;

		// �������p�̊֐�
		function initFunc () {
			// �L�����p�X�̗v�f���擾����
			var canvas = document.getElementById( 'map-canvas' ) ;

			// �Ԃ�l�̈ē�����ɂ���
			returnFunc( "" ) ;

			// �n�}�̃C���X�^���X���쐬����
			map = new google.maps.Map( canvas, {
				zoom: 18 ,
				center: new google.maps.LatLng( ido, keido ) ,
			} ) ;
		} ;

		// ���\�b�h�{�^���̃C�x���g
		document.getElementById( "method" ).onclick = function () {
			// ���s
			var result = map.setCenter( LatLngLiteral ) ;

			// �Ԃ�l���R���\�[���ɕ\��
			console.log( "�Ԃ�l:", result ) ;

			// �Ԃ�l��\��
			returnFunc( result ) ;
		}

		// ���Z�b�g�{�^���̃C�x���g
		document.getElementById( "reset" ).onclick = initFunc ;

		// �Ԃ�l�\���p�̊֐�
		function returnFunc ( value ) {
			switch ( typeof value ) {
				case "undefined" :
					value = "undefined" ;
				break ;

				case "null" :
					value = "null" ;
				break ;

				case "object" :
					try {
						value = JSON.stringify( value ) ;
					} catch (e) {
					}
				break ;
			}

			document.getElementById( "return" ).textContent = value.toString() ;
		}

		// �n�}�̕\���J�n
		initFunc() ;
});
