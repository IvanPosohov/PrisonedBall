package ru.ivanp;

import ru.ivanp.core.GameManager;
import ru.ivanp.core.views.BaseView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * Главное окно игры
 */
public class MainWindow {
	private MainWindow() {
        // Инициализируем свойства окна
        JFrame frame = new JFrame();
        frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
        // Приложение должно завершиться после закрытия окна
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prisoned Ball");

        // Создаем вьюшку и добавляем ее в окно
        BaseView view = new BaseView();
		frame.getContentPane().add(view);

        // Создаем мэнеджер управляющий логикой игры
        final GameManager gameManager = new GameManager(view);

        // Добавляем верхнее меню для старта, паузы игры
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        final JMenuItem startMenuItem = new JMenuItem("Start");
        menuBar.add(startMenuItem);
        final JMenuItem pauseMenuItem = new JMenuItem("Pause");
        pauseMenuItem.setEnabled(false);
        menuBar.add(pauseMenuItem);

        // Подписываемся на клики по меню
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.start();
                startMenuItem.setEnabled(false);
                pauseMenuItem.setEnabled(true);
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.pause();
                startMenuItem.setEnabled(true);
                pauseMenuItem.setEnabled(false);
            }
        });

        // Подписываемся на событие клика по вьюшке
        frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                // При клике ЛЕВОЙ кнопкой добавляем 1 новый шарик с координатами клика
                int count = 1;
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // При клике ПРАВОЙ кнопкой добавляем 10 новых шариков
                    count = 10;
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    // При клике КОЛЕСКОМ кнопкой добавляем 1000 новых шариков
                    count = 1000;
                }

                for (int i = 0; i < count; i++) {
                    gameManager.addBall(e.getX(), e.getY());
                }
			}
		});
	}

	public static void main(String[] args) {
        // Точка входа, создаем экземпляр главного окна
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
	}
}