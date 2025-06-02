package br.com.queimadas.alertas.domain.factory;

import br.com.queimadas.alertas.domain.entity.Alerta;
import br.com.queimadas.alertas.domain.entity.PontoDeFoco;

/**
 * Factory Method responsável por criar instâncias de {@link Alerta}
 * a partir de um {@link PontoDeFoco}, atribuindo a severidade correta.
 *
 * Limiares de intensidade (padrão):
 *  • < 500  → BAIXA
 *  • 500–799 → MEDIA
 *  • ≥ 800  → ALTA
 */
public final class AlertaFactory {

    private AlertaFactory() {
        // utilitário estático – não instanciável
    }

    /**
     * Cria um novo alerta com severidade proporcional à intensidade do foco.
     * @param foco ponto de foco que originou o alerta
     * @return instância de {@link Alerta} pronta para ser persistida
     */
    public static Alerta criar(PontoDeFoco foco) {
        if (foco == null) {
            throw new IllegalArgumentException("Ponto de foco não pode ser nulo");
        }

        Alerta alerta = new Alerta();
        alerta.setPontoDeFoco(foco);

        double intensidade = foco.getIntensidade();
        if (intensidade < 500) {
            alerta.setSeveridade(Alerta.Severidade.BAIXA);
        } else if (intensidade < 800) {
            alerta.setSeveridade(Alerta.Severidade.MEDIA);
        } else {
            alerta.setSeveridade(Alerta.Severidade.ALTA);
        }

        return alerta;
    }
}
