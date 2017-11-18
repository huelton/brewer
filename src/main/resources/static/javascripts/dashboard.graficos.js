var Brewer = Brewer || {};

Brewer.GraficoVendaPorMes =  (function(){
	
	function GraficoVendaPorMes(){	
	   this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');			
	}
	
	GraficoVendaPorMes.prototype.iniciar = function(){
		var graficoVendasPorMes = new Chart(this.ctx, {
			type: 'line',
			data: {
				labels: ['Jan','Fev','Mar','Abr','Mai','Jun'],
				datasets: [{
					label: 'Vendas por Mês',
					backgroundColor: "rgba(26,179,148,0.5)",
					pointBorderColor: "rgba(26,179,148,1)",
					pointBackGroundColor: "#fff",
					data:[10,4,5,7,9,12]
				}]
			},
			
		});
	}
	
	return GraficoVendaPorMes;
	
}());

$(function(){
	var graficoVendaPorMes = new Brewer.GraficoVendaPorMes();
	graficoVendaPorMes.iniciar();
});