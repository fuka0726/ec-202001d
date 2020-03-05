window.onload = function () {
  var $btn = document.getElementById('confirm');
  // 支払いボタンをクリックでAPIをキック
  $btn.addEventListener('click', onBuyClicked, false);
}

function onBuyClicked() {
  // Payment Request APIが使用できない環境では従来の決済ページ（/checked）に移動させる
  if (!window.PaymentRequest) {
    location.href = '/orderConfirm';
    return;
  }

  // 支払いオプションの設定をする
  var supportedInstruments = [{
    supportedMethods: ['basic-card'],
    data: {
      supportedNetworks: [
        'visa', 'mastercard', 'amex', 'discover', 'diners', 'jcb', 'unionpay'
      ]
    }
  }];

  // 支払い金額を設定する
  var details = {
    displayItems: [
      {
        label: '通常商品',
        amount: { currency: 'YEN', value: '10000' }
      },
      {
        label: 'クーポン',
        amount: { currency: 'YEN', value: '-2000' }
      }
    ],
    total: {
      label: '合計',
      amount: { currency: 'YEN', value: '8000' }
    }
  };

  // PaymentRequestオブジェクトを生成しブラウザに表示する
  var request = new PaymentRequest(supportedInstruments, details);
  request.show();
}